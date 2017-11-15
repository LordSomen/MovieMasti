package com.example.android.moviemasti.pojo;

/**
 * Created by soumyajit on 15/11/17.
 */
public class MovieDetails {

    String showVideoKey;
    String showReviews;
    String videoName;
    String videoSite;
    public MovieDetails(String key,String name , String site ,String reviews){
        showVideoKey = key;
        videoName = name;
        videoSite = site;
        showReviews = reviews;
    }

    public String getShowReviews() {
        return showReviews;
    }

    public String getShowVideoKey() {
        return showVideoKey;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoSite() {
        return videoSite;
    }

}
