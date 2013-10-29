/**
 * SMSReadMessageList.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.SMSReadMessageList
 * jiangyuchen Create at 2013-10-29 下午1:57:35
 */
package com.ngandroid.demo.content;

import java.util.ArrayList;

import android.util.Log;

/**
 * com.ngandroid.demo.content.SMSReadMessageList
 * @author jiangyuchen
 *
 * create at 2013-10-29 下午1:57:35
 */
public class SMSReadMessageList extends ArrayList<SMSReadMessage> {
    /** serialVersionUID*/
    private static final long serialVersionUID = 1350675455236903482L;
    private static final String TAG = "JustDemo SMSReadMessageList";
    
    int starterUid;//19936013,//发起短信会话的用户的id 
    String allUsers;//"4321   asd   123245   fds",//参与会话的所有的 用户id   用户名... 
    int currentPage;//1 //当前所在的页 
    int nextPage;//1//是否有下一页 
    long time;
    public int getStarterUid() {
        return starterUid;
    }
    public void setStarterUid(int starterUid) {
        this.starterUid = starterUid;
    }
    public String getAllUsers() {
        return allUsers;
    }
    public void setAllUsers(String allUsers) {
        this.allUsers = allUsers;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getNextPage() {
        return nextPage;
    }
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getAuther(int uid){
        Log.d(TAG, "data:"+allUsers);
        String[] userArray = allUsers.split("\t");
        for(int i=0;i<userArray.length; i++){
            Log.d(TAG, "index:"+i+" "+userArray[i]);
            if(Long.parseLong(userArray[2*i]) == uid){
                return userArray[2*i+1];
            }
        }
        return null;
    }
}
