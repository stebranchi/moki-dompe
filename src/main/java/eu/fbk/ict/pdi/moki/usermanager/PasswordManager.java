package eu.fbk.ict.pdi.moki.usermanager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;
import eu.fbk.ict.pdi.moki.utils.SendEmail;
import eu.fbk.ict.pdi.moki.utils.Util;

public class PasswordManager implements Service{
	
	PasswordManagerRequest password_request = null;
	HttpSession session;	
	ReturnMessage res;
	
	@Override
	public ReturnMessage run() {
		if(password_request == null) {
			res.setMessage_text("Error: data is empty");
			return res;
		}
		
		try {
			Method method = this.getClass().getMethod(password_request.getMode());
			method.invoke(this);
		} catch (Exception e) {
			res.setMessage_text("mode " + password_request.getMode() + " don't found");
		}
		return res;
	}

	@Override
	public void init(String data, HttpSession session) {
		Gson gson = new Gson();
		password_request = gson.fromJson(data, PasswordManagerRequest.class);
		this.session = session;
		
		res = new ReturnMessage();
		res.setName_service("PasswordManager");
		res.setType_service("usermanager");
		
	}
public ReturnMessage passwordForgotten() throws IOException {	
	Properties props = new Properties();
	props.load(getClass().getResourceAsStream("/config.properties"));
		
	int count = 0;	
	try {
		DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
		ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ?", password_request.getEmail());
		while (rs.next()) {
			count++;
        }
	}
	catch(Exception exc) {
		exc.printStackTrace();
		res.setMessage_text("Database error: can't get data");
		return res;
	}
	
	if(count > 0)
	{
		UUID random_hash = UUID.randomUUID();
		
		DBLayer.SQL("UPDATE user SET emailconfirmed = ? WHERE email = ?", random_hash.toString(), password_request.getEmail());
		
		String text = props.getProperty("serverUrl") +"/moki-dompe/index.jsp?page=resetPassword&module=usermanager&token=" + random_hash + "&email=" + password_request.getEmail();
		SendEmail.send(password_request.getEmail(),"Moki - Reset your password",text);
		
		res.setMessage_text("ok");
		res.setData("index.jsp?page=passwordForgotten&module=usermanager&notification=ok");
		return res;
	} else {
		res.setMessage_text("ok");
		res.setData("index.jsp?page=resetPassword&module=usermanager&notification=bad");
		return res;
	}
}
	
	public ReturnMessage resetPassword() {
		//set "yes" if the hash is correct
		if(password_request.getPassword().equals(password_request.getPassword_confirm())) 
		{
			password_request.setPassword( Util.hash(password_request.getPassword()) );
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				DBLayer.SQL("UPDATE user SET emailconfirmed = ?, password = ? WHERE email = ? AND emailconfirmed = ?", "yes", password_request.getPassword(), password_request.getEmail(), password_request.getConfirmation());
				
			}
			catch(Exception exc) {
				res.setMessage_text("Database error: can't get data");
				return res;
			}
			
			//check if the user is confirmed
			int c = 0;
			try {
				ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ? AND emailconfirmed = ?", password_request.getEmail(), "yes");
				while (rs.next()) {
					c++;
	            }
			}
			catch(Exception exc) {
				res.setMessage_text("Database error: can't get data");
				return res;
			}
			
			if(c == 0) {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=resetPassword&module=usermanager&notification=bad");
				return res;
			} else {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=resetPassword&module=usermanager&notification=resetok");
				return res;
			}
		} else {
			res.setMessage_text("ok");
			res.setData("index.jsp?page=register&module=usermanager&notification=bad");
			return res;
		}
	}

}
