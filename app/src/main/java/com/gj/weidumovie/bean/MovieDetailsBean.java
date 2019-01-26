package com.gj.weidumovie.bean;

import java.util.List;

/**
 * date:2019/1/25 20:05
 * author:陈国星(陈国星)
 * function:
 */
public class MovieDetailsBean {

    /**
     * director : 庄文强
     * duration : 130分钟
     * followMovie : 2
     * id : 20
     * imageUrl : http://mobile.bwstudent.com/images/movie/stills/ws/ws1.jpg
     * movieTypes : 动作 / 惊悚 / 犯罪
     * name : 无双
     * placeOrigin : 中国大陆,中国香港
     * posterList : ["http://mobile.bwstudent.com/images/movie/stills/ws/ws1.jpg","http://mobile.bwstudent.com/images/movie/stills/ws/ws3.jpg","http://mobile.bwstudent.com/images/movie/stills/ws/ws2.jpg","http://mobile.bwstudent.com/images/movie/stills/ws/ws4.jpg","http://mobile.bwstudent.com/images/movie/stills/ws/ws5.jpg","http://mobile.bwstudent.com/images/movie/stills/ws/ws6.jpg"]
     * rank : 0
     * shortFilmList : [{"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/ws/ws2.jpg","videoUrl":"http://mobile.bwstudent.com/video/movie/ws/ws1.mp4"},{"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/ws/ws3.jpg","videoUrl":"http://mobile.bwstudent.com/video/movie/ws/ws2.mp4"},{"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/ws/ws4.jpg","videoUrl":"http://mobile.bwstudent.com/video/movie/ws/ws3.mp4"}]
     * starring : 周润发,郭富城,张静初,冯文娟,廖启智
     * summary : 以代号“画家”（周润发 饰）为首的犯罪团伙，掌握了制造伪钞技术，难辨真伪，并在全球进行交易获取利益，引起警方高度重视。然而“画家”和其他成员的身份一直成谜，警方的破案进度遭受到了前所未有的挑战。在关键时刻，擅长绘画的李问（郭富城 饰）打开了破案的突破口，而“画家”的真实身份却让众人意想不到……
     */

    private String director;
    private String duration;
    private int followMovie;
    private int id;
    private String imageUrl;
    private String movieTypes;
    private String name;
    private String placeOrigin;
    private int rank;
    private String starring;
    private String summary;
    private List<String> posterList;
    private List<ShortFilmListBean> shortFilmList;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getFollowMovie() {
        return followMovie;
    }

    public void setFollowMovie(int followMovie) {
        this.followMovie = followMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(String movieTypes) {
        this.movieTypes = movieTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOrigin() {
        return placeOrigin;
    }

    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<String> posterList) {
        this.posterList = posterList;
    }

    public List<ShortFilmListBean> getShortFilmList() {
        return shortFilmList;
    }

    public void setShortFilmList(List<ShortFilmListBean> shortFilmList) {
        this.shortFilmList = shortFilmList;
    }

    public static class ShortFilmListBean {
        /**
         * imageUrl : http://mobile.bwstudent.com/images/movie/stills/ws/ws2.jpg
         * videoUrl : http://mobile.bwstudent.com/video/movie/ws/ws1.mp4
         */

        private String imageUrl;
        private String videoUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
