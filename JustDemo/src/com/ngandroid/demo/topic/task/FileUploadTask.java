package com.ngandroid.demo.topic.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.io.SAXReader;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ngandroid.demo.util.Configs;
import com.ngandroid.demo.util.HttpUtil;

public class FileUploadTask extends AsyncTask<File, Integer, String> {
    private static final String TAG = FileUploadTask.class.getSimpleName();
//    private static final String BOUNDARY = "-----------------------------7db1c5232222b";
    private static final String BOUNDARY = "----WebKitFormBoundaryyB6nzACf1aVQytbU";
    private static final String ATTACHMENT_SERVER = "http://img6.ngacn.cc:8080/attach.php?";
    private static final String LOG_TAG = FileUploadTask.class.getSimpleName();

    /* private InputStream is; */
    private long filesize;
    private Context context;

    private String filename;
    private String utfFilename;
    private String contentType;
    private String errorStr = null;

    static final private String attachmentsStartFlag = "attachments:'";
    static final private String attachmentsEndFlag = "'";
    static final private String attachmentsCheckStartFlag = "attachments_check:'";
    static final private String attachmentsCheckEndFlag = "'";

    static final private String picUrlStartTag = "url:'";
    static final private String picUrlEndTag = "'";

    /*
     * public FileUploadTask(InputStream is, long filesize, Context context,
     * onFileUploaded notifier, String contentType) { super(); this.is = is;
     * this.filesize = filesize; this.context = context; this.notifier =
     * notifier; this.contentType = contentType; this.filename =
     * contentType.replace('/', '.'); this.utfFilename = filename.substring(1);
     * }
     */

    public FileUploadTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    @Override
    protected String doInBackground(File... params) {

        this.filename = params[0].getName();
        this.utfFilename = filename.substring(1);
        filesize = params[0].length();
        final byte header[] = buildHeader().getBytes();
        final byte tail[] = buildTail().getBytes();

        String html = null;
        URL url;
        try {
            url = new URL(ATTACHMENT_SERVER);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("Content-Length",
                    String.valueOf(header.length + filesize + tail.length));
            conn.setRequestProperty("User-Agent", HttpUtil.USER_AGENT);
            conn.setRequestProperty("Accept-Charset", "GBK");
            conn.setRequestProperty("Cookie", Configs.getCookie(context));
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();

            byte[] buf = new byte[1024];
            int len;
            out.write(header);
            InputStream is = new FileInputStream(params[0]);
            while ((len = is.read(buf)) != -1)
                out.write(buf, 0, len);

            out.write(tail);

            is.close();
            InputStream httpInputStream = conn.getInputStream();
            for (int i = 1; (conn.getHeaderFieldKey(i)) != null; i++) {
                // Log.d(LOG_TAG, conn.getHeaderFieldKey(i) + ":"
                // + conn.getHeaderField(i));

            }
            // Log.d(LOG_TAG, "get response" + html);
            out.close();
            Log.v(TAG, new SAXReader().read(new InputStreamReader(httpInputStream, "GBK")).asXML());
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));
        }

        return html;
    }

    private String buildHeader() {
        StringBuilder sb = new StringBuilder();
        final String keys[] = { "v2", "attachment_file1_watermark",
                "attachment_file1_dscp", "attachment_file1_url_utf8_name",
                "fid", "func", "attachment_file1_img", "origin_domain", "lite" };
        final String values[] = { "1", "", "", filename, "-7", "upload", "1",
                "bbs.ngacn.cc", "xml" };

        for (int i = 0; i < keys.length; ++i) {
            sb = sb.append("--");
            sb = sb.append(BOUNDARY);
            sb = sb.append("\r\n");
            sb = sb.append("Content-Disposition: form-data; name=\"" + keys[i]
                    + "\"\r\n\r\n");
            sb = sb.append(values[i]);
            sb = sb.append("\r\n");
        }

        sb.append("--" + BOUNDARY + "\r\n");
        // sb.append("Content-Disposition: form-data; name=\"attachment_file1\"");
        sb.append("Content-Disposition: form-data; name=\"attachment_file1\"; ");
        sb.append("filename=\"");
        sb.append(filename);
        sb.append("\"");
        sb.append("\r\n");

        sb.append("Content-Type: ");
        contentType = "image/jpeg";
        sb.append(contentType);
        sb.append("\r\n\r\n");

        return sb.toString();

    }

    private String buildTail() {
        StringBuilder sb = new StringBuilder();
        /*
         * sb.append("\r\n"); sb.append("--" + BOUNDARY + "\r\n");
         * sb.append("Content-Disposition: form-data;");
         * sb.append(" name=\"attachment_file1_watermark\"\r\n\r\n\r\n");
         * 
         * 
         * sb.append("--" + BOUNDARY + "\r\n");
         * sb.append("Content-Disposition: form-data;");
         * sb.append(" name=\"attachment_file1_dscp\"\r\n\r\n\r\n");
         * 
         * sb.append("--" + BOUNDARY + "\r\n");
         * sb.append("Content-Disposition: form-data;");
         * sb.append(" name=\"attachment_file1_url_utf8_name\"\r\n\r\n");
         * sb.append(utfFilename + "\r\n");
         * 
         * sb.append("--" + BOUNDARY + "\r\n");
         * sb.append("Content-Disposition: form-data;");
         * sb.append(" name=\"func\"\r\n\r\nupload\r\n");
         * 
         * sb.append("--" + BOUNDARY + "\r\n");
         * sb.append("Content-Disposition: form-data;");
         * sb.append(" name=\"fid\"\r\n\r\n-7\r\n");
         */

        sb.append("\r\n--" + BOUNDARY + "--\r\n");

        return sb.toString();
    }

    public interface onFileUploaded {
        int finishUpload(String attachments, String attachmentsCheck,
                String picUrl);
    }

}
