package com.gj.weidumovie.bean;

/**
 * Description:根据id查询数据
 * Author:GJ<br>
 * Date:2019/1/25 14:06
 */
public class UserInfo {
    /**
     * email : 2481976676@qq.com
     * headPic : http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg
     * id : 1817
     * lastLoginTime : 1548418130000
     * nickName : 于洋
     * phone : 18811690458
     * sex : 2
     */

    private String email;
    private String headPic;
    private int id;
    private long lastLoginTime;
    private String nickName;
    private String phone;
    private int sex;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
