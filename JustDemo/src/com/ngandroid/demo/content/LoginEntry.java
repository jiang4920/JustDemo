package com.ngandroid.demo.content;


public class LoginEntry extends BaseEntry {
	private String email;
	private String password;
	
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static String uriAPI = "http://account.178.com/q_account.php?_act=client_login";
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
	@Override
	protected void formatParamas() {
		this.addParam(EMAIL, email);
		this.addParam(PASSWORD, password);
		
	} 
	
}
