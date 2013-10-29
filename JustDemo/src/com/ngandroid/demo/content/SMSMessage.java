/**
 * SMSMessage.java[V 1.0.0]
 * classes // com.ngandroid.demo.content.SMSMessage
 * jiangyuchen Create at 2013-10-29 上午10//20//57
 */
package com.ngandroid.demo.content;

import java.io.Serializable;

/**
 * com.ngandroid.demo.content.SMSMessage
 * @author jiangyuchen
 *
 * create at 2013-10-29 上午10//20//57
 */
public class SMSMessage implements Serializable {

    /** serialVersionUID*/
    private static final long serialVersionUID = -7969344906998193140L;

    private static final String TAG = "JustDemo SMSMessage";
    
    int mid;//139382,//消息id 
    long last_modify; //1378975106,//最后更新时间 
    String bit;//0,//消息类型bit 见8.1.1 
    String subject;//;bvgtfrebvhgtrfbehvgrf;,//标题 
    String from;//58,//发起人用户id 
    String time;//1378975106,//发起时间 
    String last_from;//58,//最后回复人用户id 
    int posts;//1,//回复数 
    String from_username;//;zeg;,//发起人 
    String last_from_username;//;zeg;//最后回复人 
    public int getMid() {
        return mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
    public long getLast_modify() {
        return last_modify;
    }
    public void setLast_modify(long last_modify) {
        this.last_modify = last_modify;
    }
    public String getBit() {
        return bit;
    }
    public void setBit(String bit) {
        this.bit = bit;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLast_from() {
        return last_from;
    }
    public void setLast_from(String last_from) {
        this.last_from = last_from;
    }
    public int getPosts() {
        return posts;
    }
    public void setPosts(int posts) {
        this.posts = posts;
    }
    public String getFrom_username() {
        return from_username;
    }
    public void setFrom_username(String from_username) {
        this.from_username = from_username;
    }
    public String getLast_from_username() {
        return last_from_username;
    }
    public void setLast_from_username(String last_from_username) {
        this.last_from_username = last_from_username;
    }
    
    

}
