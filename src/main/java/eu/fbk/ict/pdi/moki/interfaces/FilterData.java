package eu.fbk.ict.pdi.moki.interfaces;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eu.fbk.ict.pdi.moki.utils.Config;

public class FilterData implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("initialize");
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//database informations
		Config.server = "localhost";
		Config.dbName = props.getProperty("sqlschema");
		Config.user = props.getProperty("sqluser");
		Config.pass = props.getProperty("sqlpassword");
		
		
		//front-end pages access limitations
		HashMap<String, String []> index_white_list = Config.index_white_list;
		index_white_list.put("adminPanel", new String [] {"admin"});
		index_white_list.put("login", new String [] {"not_logged"});
		index_white_list.put("logout", new String [] {"logged"});
		index_white_list.put("register", new String [] {"not_logged"});
		index_white_list.put("registerAction", new String [] {"not_logged"});
		index_white_list.put("rolePanel", new String [] {"admin"});
		index_white_list.put("userPanel", new String [] {"logged"});
		
		//back-end services access limitations
		HashMap<String, String []> request_white_list = Config.request_white_list;
		request_white_list.put("AdminDataManager", new String [] {"admin"});
		//LoginManager all
		request_white_list.put("RegisterManager", new String [] {"not_logged"});
		request_white_list.put("RoleManager", new String [] {"admin"});
		//UserDataManager all
		
	}

}
