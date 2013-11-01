/**
 * PageUtil.java[V 1.0.0]
 * classes : com.ngandroid.demo.util.PageUtil
 * jiangyuchen Create at 2013-11-1 上午11:16:37
 */
package com.ngandroid.demo.util;


/**
 * com.ngandroid.demo.util.PageUtil
 * @author jiangyuchen
 *
 * create at 2013-11-1 上午11:16:37
 */
public class PageUtil {
    private static final String TAG = "JustDemo PageUtil";
    
    public static int nextPage(int cur, int count){
        if(cur < count){
            cur++;
        }
        return cur;
    }
    public static int prePage(int cur){
        if(cur >1){
            cur--;
        }
        return cur;
    }
    
}
