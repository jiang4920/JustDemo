/**
 * RigestEntry.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.RigestEntry
 * jiangyuchen Create at 2013-8-1 下午3:54:32
 */
package com.ngandroid.demo.content;

import java.net.URLEncoder;

import com.ngandroid.demo.util.MD5Utile;

/**
 * com.ngandroid.demo.content.RigestEntry
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-8-1 下午3:54:32
 */
public class RegistEntry {
    private static final String TAG = "JavaDemo RigestEntry";

    /**http://account.178.com/q_account.php?_act=client_register
     * nickname=test17802&email=test17802@gmail.com&password=abc123&password2=
     * abc123&question=&answer=&time=1374215552&checksum=加密验证串&dayaType=1
     */
    public String nickname = "";
    public String email = "";
    public String password = "";
    public String password2 = "";
    public String question = "";
    public String answer = "";
    
    private String time = "";
    private String checksum = "";
    static final String dataType = "1";
    
    public static final String KEY = "5742c5fe1b15a7dffa4a9d83c4698eb0";
    
    public String url = "http://account.178.com/q_account.php?_act=client_register";
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("nickname=");
        sb.append(nickname);
        sb.append("&email=");
        sb.append(email);
        sb.append("&password=");
        sb.append(password);
        sb.append("&password2=");
        sb.append(password2);
        sb.append("&question=");
        sb.append(question);
        sb.append("&answer=");
        sb.append(answer);
        sb.append("&time=");
        sb.append(""+ getTime());
        String tmp = parse("@", sb.toString());
        System.out.println("md5 source:"+tmp);
        checksum = MD5Utile.MD5(tmp+KEY);
        sb.append("&checksum=");
        sb.append(checksum);
        sb.append("&dataType=");
        sb.append(dataType);
//      Log.v(TAG, sb.toString());
//      String tmp2 = parse("@", sb.toString());
        return sb.toString();
    }
    
    public String parse(String reg, String str){
        return str.toString().replaceAll(reg, URLEncoder.encode(reg));
    }
    
    public String getTime(){
        time =  ""+System.currentTimeMillis()/1000;
        return time;
    }
    
}
