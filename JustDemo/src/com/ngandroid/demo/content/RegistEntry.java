/**
 * RigestEntry.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.RigestEntry
 * jiangyuchen Create at 2013-8-1 ����3:54:32
 */
package com.ngandroid.demo.content;

import java.net.URLEncoder;

import com.ngandroid.demo.util.MD5Utile;

/**
 * com.ngandroid.demo.content.RigestEntry
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-8-1 ����3:54:32
 */
public class RegistEntry extends BaseEntry {
    private static final String TAG = "JavaDemo RigestEntry";
    public static String uriAPI = "http://account.178.com/q_account.php?_act=client_register"; 
    /**
     * 昵称
     */
    private String nickname = "";
    /**
     * 邮箱即用户名
     */
    private String email = "";
    /**
     * 密码
     */
    private String password = "";
    /**
     * 确认密码，与密码需要相同
     */
    private String password2 = "";
    
    private String question = "";
    
    private String answer = "";
    
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
		
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
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
		
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	protected void formatParamas() {
		this.addParam("nickname", nickname);
		this.addParam("email", email);
		this.addParam("password", password);
		this.addParam("password2", password2);
		this.addParam("question", question);
		this.addParam("answer", answer);
	}
    
    
}
