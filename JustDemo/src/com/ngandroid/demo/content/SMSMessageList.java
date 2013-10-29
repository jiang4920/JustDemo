/**
 * SMSMessageList.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.SMSMessageList
 * jiangyuchen Create at 2013-10-29 上午10:24:20
 */
package com.ngandroid.demo.content;

import java.util.ArrayList;

/**
 * com.ngandroid.demo.content.SMSMessageList
 * @author jiangyuchen
 *
 * create at 2013-10-29 上午10:24:20
 */
public class SMSMessageList extends ArrayList<SMSMessage> {
    private static final String TAG = "JustDemo SMSMessageList";
    
    int rowsPerPage;//35,每页的消息数 
    int nextPage;//1,是否有下一页 
    int currentPage;//1,当前所在页 
    long time;
    public int getRowsPerPage() {
        return rowsPerPage;
    }
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public int getNextPage() {
        return nextPage;
    }
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

}
