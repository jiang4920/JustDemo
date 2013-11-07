/**
 * ErrorEntity.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.ErrorEntity
 * jiangyuchen Create at 2013-11-7 上午9:50:18
 */
package com.ngandroid.demo.content;

/**
 * com.ngandroid.demo.content.ErrorEntity
 * @author jiangyuchen
 *
 * create at 2013-11-7 上午9:50:18
 */
public class ErrorEntity {
    private static final String TAG = "JustDemo ErrorEntity";
    
    String reason;

    /**
     * @param string
     */
    public ErrorEntity(String string) {
        reason = string;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
