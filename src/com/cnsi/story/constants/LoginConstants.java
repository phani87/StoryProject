package com.cnsi.story.constants;

import java.io.Serializable;

public class LoginConstants implements Serializable{

	private String usrName;
	private String pwd;
	private String hashpwd;
	private String uniqueUserID;
	private String ba;
	private String email;
	private boolean isUser;
	
	//setters and getters
	
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getHashpwd() {
		return hashpwd;
	}
	public void setHashpwd(String hashpwd) {
		this.hashpwd = hashpwd;
	}
	public String getUniqueUserID() {
		return uniqueUserID;
	}
	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public String getBa() {
		return ba;
	}
	public void setBa(String ba) {
		this.ba = ba;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
