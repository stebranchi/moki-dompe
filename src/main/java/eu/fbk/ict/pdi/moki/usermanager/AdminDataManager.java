package eu.fbk.ict.pdi.moki.usermanager;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import com.google.gson.*;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;
import eu.fbk.ict.pdi.moki.utils.Util;

public class AdminDataManager implements Service {
	AdminDataManagerRequest user = null;
	HttpSession session;
	Gson gson = new Gson();
	ReturnMessage res;	
	
		@Override
		public ReturnMessage run() {
			
			if(user == null) {
				res.setMessage_text("ok");
				//res.setData("index.jsp?page=adminPanel&module=usermanager&notification=bad");
				res.setData("bad");
				
				//DBLayer.close();
				
				return res;
			}
			
			//check if the user logged in is an admin
			if(!Arrays.asList((String [])session.getAttribute("role")).contains("admin")) {
				res.setMessage_text("ok");
				res.setData("bad");
				return res;
			}
			
			//invoke the method requested by the user
			try {
				Method method = this.getClass().getMethod(user.getMode());
				method.invoke(this);
			} catch (Exception e) {
				res.setMessage_text("mode " + user.getMode() + " don't found");
			}
			
			/*if(user.getMode().equals("getData"))
				res = getData();
			else if(user.getMode().equals("setUserData"))
				res = setUserData();
			else if(user.getMode().equals("setActive"))
				res = setActive();
			else res.setMessage_text("mode " + user.getMode() + " don't found");*/
			
			return res;
		}

		@Override
		public void init(String data, HttpSession session) {
			user = gson.fromJson(data, AdminDataManagerRequest.class);
			this.session = session;
			
			res = new ReturnMessage();
			res.setName_service("AdminDataManager");
			res.setType_service("usermanager");
		}

		public ReturnMessage getData() {
			
			AdminDataManagerRequest usr = new AdminDataManagerRequest();
			String json_user = "[";
			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				
					String role;
					int i=0,j=0;
					
					ResultSet rs = DBLayer.SQL("SELECT * FROM role");
					while (rs.next()) {
						role = rs.getString("role");
						j=0;
						
						if(i!=0)
							json_user += ",";
						
						json_user += "{\"role\": \"" + role + "\",\"list\": [";
						
						ResultSet users = DBLayer.SQL("SELECT * FROM user");
						while (users.next()) {
							
							String[] role_list = gson.fromJson(users.getString("role"), String[].class);
							
							if(Arrays.asList(role_list).contains(role)) {
								usr.setActive(users.getString("active"));
								if(users.getString("birthdate") == null) 
									usr.setBirthdate("0000-00-00");
								else
									usr.setBirthdate(users.getString("birthdate"));
								usr.setEmail(users.getString("email"));
								usr.setEmail_confirm(users.getString("emailconfirmed"));
								usr.setName(users.getString("name"));
								usr.setOrganization(users.getString("organization"));
								usr.setPassword(users.getString("password"));
								usr.setRole(gson.fromJson(users.getString("role"), String[].class));
								
								if(j!=0)
									json_user += ",";
								
								json_user += gson.toJson(usr);
								
								j++;
							}
			            }
						
						json_user += "]}";
						
						i++;
		            }
			} catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't get data");
				return res;
			}
			
			json_user += "]";
			
			res.setMessage_text("ok");
			res.setData(gson.fromJson(json_user, Object.class));
			return res;
		}
		
		public ReturnMessage setUserData() {
			
			if(user.getBirthdate() == null)
				user.setBirthdate("0000-00-00");
			
			if(user.getOrganization().equals(""))
				user.setOrganization("-");
			
			if(user.getEmail_confirm().equals(""))
				user.setEmail_confirm("yes");
			 
			
			if((user.getPassword() != null && user.getPassword().length() < 8) || !user.getEmail().matches("^([a-zA-Z0-9])+([a-zA-Z0-9._%+-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+$") || (user.getName() != null && user.getName().length() < 2)) {
				res.setMessage_text("ok");
				res.setData("bad_data");
				return res;
			}
			try {
				
				//check if the password changed
				ResultSet p = DBLayer.SQL("SELECT * FROM user WHERE email = ?", user.getEmail_old());
				while (p.next()) {
					if(!p.getString("password").equals(user.getPassword()))
						user.setPassword(Util.hash(user.getPassword()));
	            }
			
			
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				
				//if user wants to change his mail
				if(!user.getEmail().equals(user.getEmail_old())) {
					//check if email is already taken
					ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ?", user.getEmail());
					while (rs.next()) {
						res.setMessage_text("ok");
						res.setData("mail_taken");
						return res;
		            }
				}
				
				//check if I am changing my current user
				if(user.getEmail_old().equals((String) session.getAttribute("email"))) {
					//admin can't change his role
					if(Arrays.equals(user.getRole(), (String []) session.getAttribute("role"))) {
						DBLayer.SQL("UPDATE user SET name = ?, email = ?, password = ?, emailconfirmed = ?, organization = ?, birthdate = ? WHERE email = ?", user.getName(), user.getEmail(), user.getPassword(), user.getEmail_confirm(), user.getOrganization(), user.getBirthdate(), user.getEmail_old());
						
						//set new values also in session
						session.setAttribute("name", user.getName());
						session.setAttribute("email", user.getEmail());
						session.setAttribute("organization", user.getOrganization());
						session.setAttribute("birthdate", user.getBirthdate());
						
						res.setMessage_text("ok");
						res.setData("success");
						return res;
					} else {
						res.setMessage_text("ok");
						res.setData("bad_admin");
						return res;
					}
						
				}
				
				//check if the role exists in the database
				String [] roles = user.getRole();
				int c = 0;
				ResultSet rs = DBLayer.SQL("SELECT * FROM role");
				while (rs.next()) {
					if(Arrays.asList(roles).contains(rs.getString("role"))) {
						c++;
					}
	            }
				
				if(roles.length != c && c != 0) {
					res.setMessage_text("ok");
					res.setData("bad_role");
					return res;
				}
				
				//update user with the new values
				DBLayer.SQL("UPDATE user SET name = ?, email = ?, password = ?, emailconfirmed = ?, organization = ?, birthdate = ?, role = ? WHERE email = ?", user.getName(), user.getEmail(), user.getPassword(), user.getEmail_confirm(), user.getOrganization(), user.getBirthdate(), gson.toJson(user.getRole()), user.getEmail_old());
			} catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't update data");
				return res;
			}
			
			res.setMessage_text("ok");
			res.setData("success");
			return res;
		}
		
		public ReturnMessage setActive() {
			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				
				//check if the admin is trying to disable itself
				if(user.getEmail_old().equals((String) session.getAttribute("email"))) {
					res.setMessage_text("ok");
					res.setData("bad_admin");
					return res;
				}
				
				//check if the request is a ban or an activation
				if(user.getActive().equals("0"))
					user.setActive("1");
				else if(user.getActive().equals("1"))
					user.setActive("0");
				else {
					res.setMessage_text("Backend error");
					return res;
				}
				
				//update user with the new value
				DBLayer.SQL("UPDATE user SET active = ? WHERE email = ?", user.getActive(), user.getEmail_old());
			} catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't update data");
				return res;
			}
			
			res.setMessage_text("ok");
			res.setData("success");
			return res;
		}
}
