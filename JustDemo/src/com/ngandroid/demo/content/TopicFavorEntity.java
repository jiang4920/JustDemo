/**
 * FavorDelEntity.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.FavorDelEntity
 * jiangyuchen Create at 2013-11-1 下午4:28:47
 */
package com.ngandroid.demo.content;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 * com.ngandroid.demo.content.FavorDelEntity
 * @author jiangyuchen
 *
 * create at 2013-11-1 下午4:28:47
 */
public class TopicFavorEntity {
    private static final String TAG = "JustDemo FavorDelEntity";
    
    /*
     * ./nuke.php?__lib=topic_favor&__act=topic_favor&action=del&tidarray=123,234,456&page=1
     */
    /*
     * ./nuke.php?__lib=topic_favor&__act=topic_favor&action=add&tid=6490619&lite=xml
     */
    String __lib;
    String __act;
    String action;
    String tidarray;//多条用逗号分开
    String page;
    
    String tid;
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTidarray() {
        return tidarray;
    }

    public void setTidarray(String tidarray) {
        this.tidarray = tidarray;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public UrlEncodedFormEntity getEntiry()
            throws UnsupportedEncodingException {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("__lib", "topic_favor"));
        parameters.add(new BasicNameValuePair("__act", "topic_favor"));
        parameters.add(new BasicNameValuePair("lite", "xml"));
        parameters.add(new BasicNameValuePair("action", action));
        if(action.equals("del")){
            parameters.add(new BasicNameValuePair("tidarray", tidarray));
            parameters.add(new BasicNameValuePair("page", "1"));
        }else if(action.equals("add")){
            parameters.add(new BasicNameValuePair("tid", tid));
        }
        UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "GBK");
        return formEntiry;

    }
}
