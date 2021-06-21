package eu.fbk.ict.pdi.moki.utils;

import java.util.HashMap;

public class Config {

	public static String server;
	public static String dbName;
	public static String user;
	public static String pass;
	
	/*public static HashMap<String, String []> white_list = new HashMap<String, String []>();
	 
	{
		white_list.put("AdminDataManager", new String [] {"admin"});
		//LoginManager all
		white_list.put("RegisterManager", new String [] {"not_logged"});
		white_list.put("RoleManager", new String [] {"admin"});
		//UserDataManager all 
	};*/
	
	//to-do da spostare e rendere statica
	public static HashMap<String, String []> index_white_list = new HashMap<String, String []>();
	public static HashMap<String, String []> request_white_list = new HashMap<String, String []>();
	
}
