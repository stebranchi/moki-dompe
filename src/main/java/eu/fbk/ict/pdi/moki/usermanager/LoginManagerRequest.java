package eu.fbk.ict.pdi.moki.usermanager;

public class LoginManagerRequest  {
	private String mode;
	private String email;
	private String password;
	
	LoginManagerRequest () {	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
