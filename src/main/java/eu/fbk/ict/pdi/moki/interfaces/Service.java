package eu.fbk.ict.pdi.moki.interfaces;

import javax.servlet.http.HttpSession;

import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;

public interface Service {
	
	public ReturnMessage run();
	
	public void init(String data, HttpSession session);
}
