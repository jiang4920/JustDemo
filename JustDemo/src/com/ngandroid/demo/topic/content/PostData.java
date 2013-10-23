/**
 * PostData.java[V 1.0.0]
 * classes : com.ngandroid.demo.topic.content.PostData
 * jiangyuchen Create at 2013-10-23 下午3:29:24
 */
package com.ngandroid.demo.topic.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * com.ngandroid.demo.topic.content.PostData
 * @author jiangyuchen
 *
 * create at 2013-10-23 下午3:29:24
 */
public class PostData {
    private static final String TAG = "JustDemo PostData";
    
    /** 板块ID*/
    public String fid="";
    /** 帖子ID*/
    public String tid="";
    /** 跟帖ID*/
    public String pid="";
    /** 标题*/
    private String post_subject;
    /** 内容*/
    private String post_content;
    /** new/reply*/
    private String action;
    
    private PostAttachmentEntry attachment;
    
    public String getFid() {
        return fid;
    }


    public void setFid(String fid) {
        this.fid = fid;
    }


    public String getTid() {
        return tid;
    }


    public void setTid(String tid) {
        this.tid = tid;
    }


    public String getPid() {
        return pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }


    public String getPost_subject() {
        return post_subject;
    }


    public void setPost_subject(String post_subject) {
        this.post_subject = post_subject;
    }


    public String getPost_content() {
        return post_content;
    }


    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }


    public String getAction() {
        return action;
    }


    public void setAction(String action) {
        this.action = action;
    }


    public PostAttachmentEntry getAttachment() {
		return attachment;
	}


	public void setAttachment(PostAttachmentEntry attachment) {
		this.attachment = attachment;
	}


	public List<NameValuePair> getEntry(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("step", "2"));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("action", action));
        params.add(new BasicNameValuePair("fid",fid));
        params.add(new BasicNameValuePair("tid", tid));
        params.add(new BasicNameValuePair("_ff", ""));
        params.add(new BasicNameValuePair("attachments", ""));
        params.add(new BasicNameValuePair("attachments_check", ""));
        params.add(new BasicNameValuePair("bit_data", ""));
        params.add(new BasicNameValuePair("post_subject", post_subject));
        params.add(new BasicNameValuePair("post_content", post_content));
        return params;
    }
    
}
