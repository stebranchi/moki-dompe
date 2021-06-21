package eu.fbk.ict.pdi.moki.usermanager;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;
import eu.fbk.ict.pdi.moki.utils.Util;
import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;

public class LoginManager implements Service{
	//to be initialized
	LoginManagerRequest user = null;
	HttpSession session;	
	Gson gson = new Gson();
	ReturnMessage res;
	
		@Override
		public ReturnMessage run() {
			
			if(user == null) {
				res.setMessage_text("Error: data is empty");
				return res;
			}
			
			//invoke the method requested by the user
			try {
				Method method = this.getClass().getMethod(user.getMode());
				method.invoke(this);
			} catch (Exception e) {
				res.setMessage_text("mode " + user.getMode() + " don't found");
			}
			
			/*if(user.getMode().equals("login"))
				res = login();
			else if(user.getMode().equals("logout"))
				res = logout();
			else res.setMessage_text("mode " + user.getMode() + "don't found");*/
			
			return res;
		}

		@Override
		public void init(String data, HttpSession session) {
			user = gson.fromJson(data, LoginManagerRequest.class);
			this.session = session;
			
			res = new ReturnMessage();
			res.setName_service("LoginManager");
			res.setType_service("usermanager");
		}

		public ReturnMessage login() {
			
			//check if the user is already logged
			if (session.getAttribute("email") != null) {
				res.setMessage_text("ok");
				res.setData("index.jsp");
				return res;
			}
			
			int count = 0;
			
			user.setPassword( Util.hash(user.getPassword()) );
			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ? AND password = ? AND emailconfirmed = ? AND active = ?", user.getEmail(), user.getPassword(), "yes", "1");
				while (rs.next()) {
					count++;
					if(count > 1) {
						//why there are two users that are equal??
						session.invalidate();
						res.setMessage_text("Database error: two users has the same email");
						return res;
						
					}
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					DBLayer.SQL("UPDATE user SET lastlogin = ? WHERE email = ?", LocalDateTime.now().format(formatter).toString(), rs.getString("email"));
					//put the user data in the session
					session.setAttribute("name", rs.getString("name"));
					session.setAttribute("email", rs.getString("email"));
					session.setAttribute("lastlogin", rs.getString("lastlogin"));
					session.setAttribute("organization", rs.getString("organization"));
					if(rs.getString("birthdate") == null)
						session.setAttribute("birthdate", "0000-00-00");
					else
						session.setAttribute("birthdate", rs.getString("birthdate"));
					session.setAttribute("role", gson.fromJson(rs.getString("role"), String[].class));
					
					//get rights from the role
					int c = 0;
					for(String r: (String []) session.getAttribute("role")) {
						ResultSet rights = DBLayer.SQL("SELECT * FROM role WHERE role = ?", r);
						while (rights.next()) {
							if(c == 0)  {
								session.setAttribute("readright", rights.getInt("readright"));
								session.setAttribute("writeright", rights.getInt("writeright"));
								session.setAttribute("updateright", rights.getInt("updateright"));
								session.setAttribute("deleteright", rights.getInt("deleteright"));
							} else {
								if(rights.getInt("readright") == 1)
									session.setAttribute("readright", rights.getInt("readright"));
								if(rights.getInt("writeright") == 1)
									session.setAttribute("writeright", rights.getInt("writeright"));
								if(rights.getInt("updateright") == 1)
									session.setAttribute("updateright", rights.getInt("updateright"));
								if(rights.getInt("deleteright") == 1)
									session.setAttribute("deleteright", rights.getInt("deleteright"));
							}
							
							c++;
						}
					}
					
	            }
			
			
				if(count == 0) {
					//check if the user is banned
					rs = DBLayer.SQL("SELECT * FROM user WHERE email = ? AND password = ? AND emailconfirmed = ?", user.getEmail(), user.getPassword(), "yes");
					while (rs.next()) {
						res.setMessage_text("ok");
						res.setData("index.jsp?page=login&module=usermanager&notification=ban");
						return res;
		            }
					
					//check if the user has to confirm his mail
					rs = DBLayer.SQL("SELECT * FROM user WHERE email = ? AND password = ?", user.getEmail(), user.getPassword());
					while (rs.next()) {
						res.setMessage_text("ok");
						res.setData("index.jsp?page=login&module=usermanager&notification=not_confirmed");
						return res;
		            }
					
					//mail or password incorrect
					res.setMessage_text("ok");
					res.setData("index.jsp?page=login&module=usermanager&notification=incorrect");
					return res;
					
				}
			}
			catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't get data");
				return res;
			}
			
			res.setMessage_text("ok");
			res.setData("index.jsp");
			return res;
			
		}
		
		public ReturnMessage logout() {
			
			//check if the user is already logged
			if (session.getAttribute("email") == null) {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=login&module=usermanager&notification=bad");
				return res;
			}
			
			if(session != null)
				session.invalidate();
			res.setMessage_text("ok");
			res.setData("index.jsp?notification=logout");
			return res;
		}
	}