package eu.fbk.ict.pdi.moki.usermanager;

import java.lang.reflect.Method;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;

public class RegisterManager implements Service{
	
	//to be initialized
	RegisterManagerRequest user = null;
	HttpSession session;	
	ReturnMessage res;
	
	@Override
	public ReturnMessage run() {
		
		if(user == null) {
			res.setMessage_text("Error: data is empty");
			return res;
		}
		
		//check if the user is already logged
		if (session.getAttribute("email") != null) {
			res.setMessage_text("ok");
			res.setData("index.jsp?page=register&module=usermanager&notification=bad");
			return res;
		}
		
		//invoke the method requested by the user
		try {
			Method method = this.getClass().getMethod(user.getMode());
			method.invoke(this);
		} catch (Exception e) {
			res.setMessage_text("mode " + user.getMode() + " don't found");
		}
		
		/*if(user.getMode().equals("register")) 
			res = register();
		else if(user.getMode().equals("confirmMail"))
			res = confirm_mail();
		else res.setMessage_text("mode " + user.getMode() + "don't found");*/
		
		return res;
	}

	@Override
	public void init(String data, HttpSession session) {
		Gson gson = new Gson();
		user = gson.fromJson(data, RegisterManagerRequest.class);
		this.session = session;
		
		res = new ReturnMessage();
		res.setName_service("RegisterManager");
		res.setType_service("usermanager");
	}
	
	/*public ReturnMessage register() throws IOException {		
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream("/config.properties"));
		if(user.getPassword().equals(user.getPassword_confirm()))
		{
			// check if user already exist
			int count = 0;			
			try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ?", user.getEmail());
				while (rs.next()) {
					count++;
	            }
			}
			catch(Exception exc) {
				exc.printStackTrace();
				res.setMessage_text("Database error: can't get data");
				return res;
			}
			
			if(count == 0) 
			{
				// if not exist
				//check if password and confirm_password are equal
				if(!user.getPassword().equals(user.getPassword_confirm())) {
					res.setMessage_text("ok");
					res.setData("index.jsp?page=login&module=usermanager&notification=bad");
					return res;
				}
					
				
				//check if data is correct
				if((user.getPassword() != null && user.getPassword().length() < 8) || !user.getEmail().matches("^([a-zA-Z0-9])+([a-zA-Z0-9._%+-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+$") || (user.getName() != null && user.getName().length() < 2)) {
					res.setMessage_text("ok");
					res.setData("index.jsp?page=login&module=usermanager&notification=bad");
					return res;
				}
				
				//hash the password
				user.setPassword( Util.hash(user.getPassword()) );
				
				
				// generate confirmation mail hash
				UUID random_hash = UUID.randomUUID();
				
				//insert the user in the database
				try {					
					DBLayer.SQL("INSERT INTO user VALUES (?,?,?,?,?,?,?,?)", user.getName(), user.getEmail(), user.getPassword(), random_hash.toString(), "-", "2000-01-01", "[\"user\", \"editor\"]", "1");
				}
				catch(Exception exc) {
					res.setMessage_text("Database error: can't insert data");
					return res;
				}
				
				//send via mail the confirmation link
				
				System.out.println(props.getProperty("serverUrl") +"/moki-dompe/index.jsp?page=registerAction&module=usermanager&confirmation=" + random_hash + "&email=" + user.getEmail());
				String text = props.getProperty("serverUrl") +"/moki-dompe/index.jsp?page=registerAction&module=usermanager&confirmation=" + random_hash + "&email=" + user.getEmail();
				SendEmail.send(user.getEmail(),"Moki - Confirm your account",text);
				
			} else {
				res.setMessage_text("ok");
				res.setData("index.jsp?page=register&module=usermanager&notification=exist");
				return res;
			}
		} else {
			res.setMessage_text("ok");
			res.setData("index.jsp?page=register&module=usermanager&notification=bad");
			return res;
		}
		
		res.setMessage_text("ok");
		res.setData("index.jsp?page=register&module=usermanager&notification=ok");
		return res;
	}*/

	public ReturnMessage confirmMail() {
		//set "yes" if the hash is correct
		try {
			DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
			DBLayer.SQL("UPDATE user SET emailconfirmed = ? WHERE email = ? AND emailconfirmed = ?", "yes",user.getEmail(), user.getConfirmation());
			
		}
		catch(Exception exc) {
			res.setMessage_text("Database error: can't get data");
			return res;
		}
		
		//check if the user is confirmed
		int c = 0;
		try {
			DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
			ResultSet rs = DBLayer.SQL("SELECT * FROM user WHERE email = ? AND emailconfirmed = ?", user.getEmail(), "yes");
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
			res.setData("index.jsp?page=login&module=usermanager&notification=bad");
			return res;
		} else {
			res.setMessage_text("ok");
			res.setData("index.jsp?page=login&module=usermanager&notification=confirmed");
			return res;
		}
	}
}
