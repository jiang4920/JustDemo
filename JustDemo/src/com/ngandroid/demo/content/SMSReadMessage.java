/**
 * SMSReadMessage.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.SMSReadMessage
 * jiangyuchen Create at 2013-10-29 下午1:54:22
 */
package com.ngandroid.demo.content;

import android.util.Log;

/**
 * com.ngandroid.demo.content.SMSReadMessage
 * @author jiangyuchen
 *
 * create at 2013-10-29 下午1:54:22
 */
public class SMSReadMessage {
    private static final String TAG = "JustDemo SMSReadMessage";
    
    String subject;//"xxoo...",   //标题 
    String content;//"xxoo...xxoo...",   //内容 
    int from;//123245,//发信人uid 
    long time;//1377079518,//发信时间 
    int id;//178492//消息id
    String data;//参与者,空格分开
    
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getFrom() {
        return from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    
}
