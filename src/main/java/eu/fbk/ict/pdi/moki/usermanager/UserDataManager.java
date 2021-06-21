package eu.fbk.ict.pdi.moki.usermanager;

import java.lang.reflect.Method;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;
import eu.fbk.ict.pdi.moki.utils.Util;

public class UserDataManager implements Service{

	UserDataManagerRequest user = null;
	HttpSession session;	
	ReturnMessage res;
	
		@Override
		public ReturnMessage run() {
			
			if(user == null) {
				res.setMessage_text("Error: data is empty");
				return res;
			}
			
			//check if the user is logged
			if (session.getAttribute("email") == null) {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=userPanel&module=usermanager&notification=bad");
				return res;
			}
			
			//invoke the method requested by the user
			try {
				Method method = this.getClass().getMethod(user.getMode());
				method.invoke(this);
			} catch (Exception e) {
				res.setMessage_text("mode " + user.getMode() + " don't found");
			}
			
			/*if(user.getMode().equals("getUserData"))
				res = getUserData();
			else if(user.getMode().equals("setUserData"))
				res = setUserData();
			else res.setMessage_text("mode " + user.getMode() + "don't found");*/
			
			return res;
		}

		@Override
		public void init(String data, HttpSession session) {
			Gson gson = new Gson();
			user = gson.fromJson(data, UserDataManagerRequest.class);
			this.session = session;
			
			res = new ReturnMessage();
			res.setName_service("UserDataManager");
			res.setType_service("usermanager");
		}

		public ReturnMessage getUserData() {
			UserDataManagerRequest sendUser = new UserDataManagerRequest();
			
			//takes attributes from session
			sendUser.setName((String) session.getAttribute("name"));
			sendUser.setEmail((String) session.getAttribute("email"));
			sendUser.setOrganization((String) session.getAttribute("organization"));
			if((String) session.getAttribute("birthdate") == null) 
				sendUser.setBirthdate("0000-00-00");
			else
				sendUser.setBirthdate((String) session.getAttribute("birthdate"));
			sendUser.setRole((String []) session.getAttribute("role"));
			
			//get rights according to the role
			sendUser.setRead_right((int) session.getAttribute("readright"));
			sendUser.setWrite_right((int) session.getAttribute("writeright"));
			sendUser.setUpdate_right((int) session.getAttribute("updateright"));
			sendUser.setDelete_right((int) session.getAttribute("deleteright"));
			
			res.setMessage_text("ok");
			res.setData(sendUser);
			return res;
		}
		
		public ReturnMessage setUserData() {
			
			//check if data are correct
			if((user.getNew_password() != null && user.getNew_password().length() < 8) || !user.getEmail().matches("^([a-zA-Z0-9])+([a-zA-Z0-9._%+-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+$") || (user.getName() != null && user.getName().length() < 2)) {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=userPanel&module=usermanager&notification=bad_data");
				return res;
			}
			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				
				//if user wants to change his mail
				if(!user.getEmail().equals((String) session.getAttribute("email"))) {
					//check if email is already taken
					ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ?", user.getEmail());
					while (rs.next()) {
						res.setMessage_text("ok");
						res.setData("index.jsp?page=userPanel&module=usermanager&notification=mail_taken");
						return res;
		            }
				}
				
				if(!(user.getOld_password() == null || user.getOld_password().equals(""))) {
					//change password
					DBLayer.SQL("UPDATE user SET password = ? WHERE email = ? AND password = ?", Util.hash(user.getNew_password()), user.getEmail(), Util.hash(user.getOld_password()));
					
					//check if password has changed
					int c=0;
					ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ? AND password = ?", user.getEmail(), Util.hash(user.getNew_password()));
					while (rs.next()) {
						c++;
		            }
					if(c == 0) {
						res.setMessage_text("ok");
						res.setData("index.jsp?page=userPanel&module=usermanager&notification=pw_incorrect");
						return res;
					}
					
				}
				
				if(user.getBirthdate() == null)
					user.setBirthdate("0000-00-00");
				
				if(user.getOrganization().equals(""))
					user.setOrganization("-");
				
				//to-do se cambio la mail mando un nuovo link di conferma mail
				
				//update user with the new values
				DBLayer.SQL("UPDATE user SET name = ?, email = ?, organization = ?, birthdate = ? WHERE email = ?", user.getName(), user.getEmail(), user.getOrganization(), user.getBirthdate(), (String) session.getAttribute("email"));
				
				int c=0;
				ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE name = ? AND email = ? AND organization = ? AND birthdate = ?", user.getName(), user.getEmail(), user.getOrganization(), user.getBirthdate());
				while (rs.next()) {
					c++;
	            }
				if(c == 0) {
					res.setMessage_text("ok");
					res.setData("index.jsp?page=userPanel&module=usermanager&notification=bad");
					return res;
				}
				
			} catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't update data");
				return res;
			}
			
			//set new values also in session
			session.setAttribute("name", user.getName());
			session.setAttribute("email", user.getEmail());
			session.setAttribute("organization", user.getOrganization());
			session.setAttribute("birthdate", user.getBirthdate());
			
			res.setMessage_text("ok");
			res.setData("index.jsp?page=userPanel&module=usermanager&notification=success");
			return res;
		}
	
}
