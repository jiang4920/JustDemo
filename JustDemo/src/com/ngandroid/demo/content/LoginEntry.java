package com.ngandroid.demo.content;


/**
 * com.ngandroid.demo.content.LoginEntry
 * @author jiangyuchen
 *
 * create at 2013-8-6 下午5:53:04
 */
public class LoginEntry extends BaseEntry {
	/** 用户名*/
	private String email;  
	/** 密码*/
	private String password;
	
	private int keepLogin;
	
	private int type;
	
	public static final int TYPE_LOGIN = 0;
	public static final int TYPE_CHECK_LOGIN = 1;
	
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static String uriAPI = "http://account.178.com/q_account.php?_act=client_login";
	
	
	public int getKeepLogin() {
        return keepLogin;
    }
    public void setKeepLogin(int keepLogin) {
        this.keepLogin = keepLogin;
    }
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
	
	public void setPostType(int type){
	    
	}
	
}
