/**
 * SMSSendEntity.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.SMSSendEntity
 * jiangyuchen Create at 2013-10-29 下午4:23:08
 */
package com.ngandroid.demo.content;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 * com.ngandroid.demo.content.SMSSendEntity
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-29 下午4:23:08
 */
public class SMSSendEntity {
    private static final String TAG = "JustDemo SMSSendEntity";


    String to;//短消息接收人id或用户名
    String subject;//标题
    String content;//消息内容
    int mid;//回复的短消息id

    private String act;


    public String getAct() {
        return act;
    }


    public void setAct(String act) {
        this.act = act;
    }

    public String getTo() {
        return to;
    }


    public void setTo(String to) {
        this.to = to;
    }


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


    public int getMid() {
        return mid;
    }


    public void setMid(int mid) {
        this.mid = mid;
    }

    public UrlEncodedFormEntity getEntiry()
            throws UnsupportedEncodingException {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("__lib", "message"));
        parameters.add(new BasicNameValuePair("__act", "message"));
        parameters.add(new BasicNameValuePair("lite", "js"));
        parameters.add(new BasicNameValuePair("subject", subject));
        parameters.add(new BasicNameValuePair("content", content));
        parameters.add(new BasicNameValuePair("act", act));
        if (act.equals("new")) {
            parameters.add(new BasicNameValuePair("to", to));
        } else if (act.equals("reply")) {
            parameters.add(new BasicNameValuePair("mid", "" + mid));
        }
        UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "GBK");
        return formEntiry;

    }

}
