package com.example.android.moviemasti.pojo;

/**
 * Created by soumyajit on 15/11/17.
 */
public class MovieDetails {

    String showVideoKey;
    String videoName;
    String videoSite;
    String reviewAuthor;
    String reviewContent;
    String reviewUrl;
    public MovieDetails(String key,String name , String site ,String author,String content , String url){
        showVideoKey = key;
        videoName = name;
        videoSite = site;
        reviewAuthor = author;
        reviewContent = content;
        reviewUrl = url;
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

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }
}
