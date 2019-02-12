package com.gj.weidumovie.bean;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/30 10:59
 */
public class FindMessageList {

    /**
     * content : 中国足球的希望会是你嘛
     * id : 2
     * pushTime : 1533953917000
     * status : 1
     * title : 足球
     * userId : 5
     */

    private String content;
    private int id;
    private long pushTime;
    private int status;
    private String title;
    private int userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPushTime() {
        return pushTime;
    }

    public void setPushTime(long pushTime) {
        this.pushTime = pushTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
