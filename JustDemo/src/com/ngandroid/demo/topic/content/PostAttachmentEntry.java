package com.ngandroid.demo.topic.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ngandroid.demo.util.NGAURL;

public class PostAttachmentEntry {
	private static final String TAG = "JustDemo PostAttachment.java";
	
	/*
	 * v2:1,	//固定为1
attachment_file1:file,//附件文件
attachment_file1_watermark:'tl', //水印位置tl/tr/bl/br 左上右上左下右下 不设为无水印
attachment_file1_dscp:'gvsrf',//附件的说明
attachment_file1_url_utf8_name:'gvsrf',//附件文件UTF8编码文件原名再urlencode
fid:123,//发帖所在的版面id
func:'upload'
	 */
	
	public static final String URL_POST = NGAURL.URL_BASE+"/post.php?";
	public static final String URL_ATTACH = "http://img6.ngacn.cc:8080/attach.php?";
	
	private String fid;
	
	
	/**
	 * 附件文件
	 */
	private File attachementFile;
	
	public String getFid() {
		return fid;
	}


	public void setFid(String fid) {
		this.fid = fid;
	}


	public File getAttachementFile() {
		return attachementFile;
	}


	public void setAttachementFile(File attachementFile) {
		this.attachementFile = attachementFile;
	}


	public List<NameValuePair> getEntry(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("v2", "1"));
        params.add(new BasicNameValuePair("attachment_file1", "file"));
        params.add(new BasicNameValuePair("attachment_file1_watermark", ""));
        params.add(new BasicNameValuePair("attachment_file1_dscp",""));
        params.add(new BasicNameValuePair("attachment_file1_url_utf8_name", getAttachementFile().getName()));
        params.add(new BasicNameValuePair("fid", fid));
        params.add(new BasicNameValuePair("func", "upload"));
        params.add(new BasicNameValuePair("lite", "xml"));
        return params;
    }
	
}
