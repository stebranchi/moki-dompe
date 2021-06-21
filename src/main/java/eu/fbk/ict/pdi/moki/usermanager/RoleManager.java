package eu.fbk.ict.pdi.moki.usermanager;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;

public class RoleManager implements Service {
	RoleManagerRequest role = null;
	HttpSession session;
	Gson gson = new Gson();
	ReturnMessage res;
	
		@Override
		public ReturnMessage run() {
			
			if(role == null) {
				res.setMessage_text("Error: data is empty");
				return res;
			}
			
			//check if the user logged in is an admin
			if(!Arrays.asList((String [])session.getAttribute("role")).contains("admin")) {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=adminPanel&module=usermanager&notification=bad");
				return res;
			}
			
			//invoke the method requested by the user
			try {
				Method method = this.getClass().getMethod(role.getMode());
				method.invoke(this);
			} catch (Exception e) {
				res.setMessage_text("mode " + role.getMode() + " don't found");
			}
			
			/*if(role.getMode().equals("getRoles"))
				res = getRoles();
			else if(role.getMode().equals("create"))
				res = create();
			else res.setMessage_text("mode " + role.getMode() + "don't found");*/
			
			return res;
		}

		@Override
		public void init(String data, HttpSession session) {
			role = gson.fromJson(data, RoleManagerRequest.class);
			this.session = session;
			
			res = new ReturnMessage();
			res.setName_service("RoleManager");
			res.setType_service("usermanager");
		}

		public ReturnMessage getRoles() {
			String json_roles = "[";
			int i = 0;
			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				
				ResultSet roles = DBLayer.SQL("SELECT * FROM role");
				while (roles.next()) {
					
					if(i!=0)
						json_roles += ",";
					
					json_roles += "\"" + roles.getString("role") + "\"";
					
					i++;
	            }
			} catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't get data");
				return res;
			}
			
			json_roles += "]";
			
			res.setMessage_text("ok");
			res.setData(gson.fromJson(json_roles, Object.class));
			return res;
		}
		
		public ReturnMessage create() {
			
			//set rights as 0 or 1
			if(role.getRead() == null) 
				role.setRead("0");
			else
				role.setRead("1");
			
			if(role.getWrite() == null) 
				role.setWrite("0");
			else
				role.setWrite("1");
			
			if(role.getUpdate() == null) 
				role.setUpdate("0");
			else
				role.setUpdate("1");
			
			if(role.getDelete() == null) 
				role.setDelete("0");
			else
				role.setDelete("1");
			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				
				
				DBLayer.SQL("INSERT INTO role VALUES (?,?,?,?,?)", role.getName(), role.getRead(), role.getWrite(), role.getUpdate(), role.getDelete());
			} catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't insert data");
				return res;
			}
			
			res.setMessage_text("ok");
			res.setData("index.jsp?page=rolePanel&module=usermanager&notification=role_created");
			return res;
		}
}
