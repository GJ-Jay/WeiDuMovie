package com.gj.weidumovie.bean;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/27 21:13
 */
public class BuyTicket {


    /**
     * amount : 9
     * beginTime : 20:00:00
     * cinemaName : CGV影城（清河店）
     * createTime : 1548644933000
     * endTime : 21:48:00
     * id : 6223
     * movieName : 无双
     * orderId : 20190128110853499
     * price : 0.15
     * screeningHall : 7号厅
     * status : 1
     * userId : 1792
     */

    private int amount;
    private String beginTime;
    private String cinemaName;
    private long createTime;
    private String endTime;
    private int id;
    private String movieName;
    private String orderId;
    private double price;
    private String screeningHall;
    private int status;
    private int userId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getScreeningHall() {
        return screeningHall;
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
