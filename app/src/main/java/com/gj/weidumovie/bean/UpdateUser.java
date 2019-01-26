package com.gj.weidumovie.bean;

/**
 * Description:修改用户信息
 * Author:GJ<br>
 * Date:2019/1/25 14:46
 */
public class UpdateUser {

    /**
     * id : 0
     * nickName : 真的是你的益达吗
     * sex : 2
     */

    private int id;
    private String nickName;
    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
