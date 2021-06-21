package eu.fbk.ict.pdi.moki.usermanager;

public class UserDataManagerRequest {
	private String mode;
	private String name;
	private String email;
	private String organization;
	private String birthdate;
	private String[] role;
	private String old_password;
	private String new_password;
	private int read_right;
	private int write_right;
	private int update_right;
	private int delete_right;
	
	UserDataManagerRequest() { }
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String[] getRole() {
		return role;
	}
	public void setRole(String[] role) {
		this.role = role;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getNew_password() {
		return new_password;
	}
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	public String getOld_password() {
		return old_password;
	}
	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public int getRead_right() {
		return read_right;
	}

	public void setRead_right(int read_right) {
		this.read_right = read_right;
	}

	public int getWrite_right() {
		return write_right;
	}

	public void setWrite_right(int write_right) {
		this.write_right = write_right;
	}

	public int getDelete_right() {
		return delete_right;
	}

	public void setDelete_right(int delete_right) {
		this.delete_right = delete_right;
	}

	public int getUpdate_right() {
		return update_right;
	}

	public void setUpdate_right(int update_right) {
		this.update_right = update_right;
	}
}
