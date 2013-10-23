/**
 * PostEntry.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.PostEntry
 * jiangyuchen Create at 2013-10-23 上午10:21:34
 */
package com.ngandroid.demo.content;

import com.ngandroid.demo.util.NGAURL;

/**
 * com.ngandroid.demo.content.PostEntry
 * @author jiangyuchen
 *
 * create at 2013-10-23 上午10:21:34
 */
public class PostEntry extends BaseEntry {
    private static final String TAG = "JustDemo PostEntry";

    private String fid;
    private String pid;
    private String action;
    private String tid;
    private String post_subject;
    private String post_content;
    public static String URL = NGAURL.URL_BASE + "/post.php?lite=xml";
    
    /**
     * <p>Title: formatParamas</p>
     * <p>Description: </p>
     * @see com.ngandroid.demo.content.BaseEntry#formatParamas()
     */
    @Override
    protected void formatParamas() {
        this.addParam("pid", pid);
        this.addParam("action", action);
        this.addParam("fid", fid);
        this.addParam("tid", tid);
        this.addParam("post_subject", post_subject);
        this.addParam("post_content", post_content);
    }


    public String getFid() {
        return fid;
    }
    
    
    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getPid() {
        return pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }


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
}
