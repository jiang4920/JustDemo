package com.ngandroid.demo.content;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import android.util.Log;

public class UserResponse extends Response {
    private static final String TAG = "JustDemo UserEntry.java";

    public int uid;
    public String email;
    public String nickname;
    public int expiretime;
    public boolean hasResult;

    /**
     * <p>
     * Title: parse
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param doc
     * @see com.ngandroid.demo.content.Response#parse(org.dom4j.Document)
     */
    @Override
    public Response parse(Document doc) {
        Node root = doc.selectSingleNode("/data/data");//
        Log.v(TAG, "child count:" + root.getText());
        Element el = (Element) root;
        if (el.elementTextTrim("expiretime")==null) { // 返回只有一个数据节点即认为是注册返回的uid
            Log.v(TAG, "" + (uid = Integer.parseInt(el.getTextTrim())));
        } else {

            Log.v(TAG, "" + (uid = Integer.parseInt(el.elementText("uid"))));
            Log.v(TAG, "" + (email = el.elementText("email")));
            Log.v(TAG,
                    ""
                            + (expiretime = Integer.parseInt(el
                                    .elementText("expiretime"))));
            Log.v(TAG, "" + (nickname = el.elementText("nickname")));

        }
        return this;
    }
}
