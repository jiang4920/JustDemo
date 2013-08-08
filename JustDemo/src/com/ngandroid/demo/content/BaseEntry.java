package com.ngandroid.demo.content;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.ngandroid.demo.util.MD5Util;

public abstract class BaseEntry {
    protected static final String DATA_TYPE = "&dataType=1";
    protected static final String DATA_MD5_KEY = "&checksum=";
    public static final String KEY = "5742c5fe1b15a7dffa4a9d83c4698eb0";
    private static final String TAG = "Entry";

    private List<PostParam> mParams = new ArrayList<BaseEntry.PostParam>();

    private PostParam mTimeParam;

    public BaseEntry() {
    }

    /**
     * 将POST参数转化为URL编码
     * 
     * @param source
     * @return
     */
    String urlEncode(String source) {
        return URLEncoder.encode(source);
    }

    /**
     * 获取用户参数
     * 
     * @param isUrlEncode
     *            是否需要转化为URL编码
     * @return
     */
    public String getParams() {
        StringBuffer sb = new StringBuffer();
        for (PostParam param : mParams) {
            sb.append(param.key + "=");
            sb.append(urlEncode(param.value) + "&");
        }
        String paramsString = sb.toString().substring(0,
                sb.toString().length() - 1); // 去掉最后一个“&”
        return paramsString + getTimeParam().toString();
    }

    private PostParam getTimeParam() {
        if (mTimeParam == null) {
            PostParam param = new PostParam();
            param.key = "time";
            param.value = "" + getTime();
            mTimeParam = param;
        }
        return mTimeParam;
    }

    protected abstract void formatParamas();

    /**
     * <p>Title: getPostBody</p>
     * <p>Description: 将POST参数转化为格式化后的参数</p>
     * @return
     */
    public String getPostBody() {
        formatParamas();
        return getParams() + getChecksumParam().toString() + DATA_TYPE;
    }

    /**
     * <p>Title: getChecksumParam</p>
     * <p>Description: 获取checksum值，这个值就是把所有的有效参数经过urlencode后再进行MD5加密，checksum就是加密后的数据</p>
     * @return 经过MD5加密后的checksum数据
     */
    private PostParam getChecksumParam() {
        PostParam param = new PostParam();
        param.key = "checksum";
        param.value = MD5Util.MD5(getParams() + KEY);
        return param;
    }

    public void setParams(List<PostParam> params) {
        mParams = params;
    }

    public void addParam(String key, String value) {
        PostParam param = new PostParam();
        param.key = key;
        param.value = value;
        if (key.equals("nickname")) {
            param.hasEncode = true;
        }
        mParams.add(param);
    }

    public static long getTime() {
        return System.currentTimeMillis() / 1000;
    }

    public class PostParam {
        public String key;
        public String value;
        public boolean hasEncode;

        public String toString() {
            return "&" + key + "=" + value;
        }
    }
}
