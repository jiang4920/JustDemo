/**
 * UserInfoEntry.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.UserInfoEntry
 * jiangyuchen Create at 2013-10-25 下午3:02:30
 */
package com.ngandroid.demo.content;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * com.ngandroid.demo.content.UserInfoEntry
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-25 下午3:02:30
 */
public class UserInfoEntity {

    public String uid;//用户id
    public String username;//用户名
    public String gid;//用户组id
    public String groupid;//用户组
    public String memberid;//用户组id
    public String group;//头衔
    public String posts;//发帖数
    public String fame;//威望 除10为威望显示值
    public String rvrc;
    public String money;//金钱 铜币数
    public String title;//头衔
    public String honor;
    public String verified;//激活状态 0为未激活 1为激活 -1为被nuke禁止发言 小于-1为其他原因禁止发言
    public String yz;
    public String lastpost;//最后发帖时间
    public String bit;//用户bit 
    public String regdate;//注册日期 
    public String muteTime;//禁言到期时间， 到期时间>当前时间 为禁言状态
    public String avatar;//头像
    public String sign;//签名
    public String email;
    public String userForum;
    public String items;//为2时表示用户有道具
    public String _admin;//拥有管理员权限 
    public String _super;//拥有超级版主权限 
    public String _greater;//拥有版主权限 
    public String _lesser;//拥有次级版主权限
    public String getAvatar() {
        Pattern pat = Pattern.compile("http:.*(jpg|png)");   
        Matcher mat = pat.matcher("http"+avatar.split("http")[1]);   
        if(mat.find()){
            avatar = mat.group();
        }
        return avatar;
    }
}
