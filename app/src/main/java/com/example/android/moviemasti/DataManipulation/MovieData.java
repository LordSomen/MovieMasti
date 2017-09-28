package com.example.android.moviemasti.DataManipulation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by soumyajit on 26/9/17.
 */

public class MovieData implements Parcelable{

    private long movieId;
    private double movieVoteAverage;
    private String movieTitle;
    private String moviePosterPath;
    private double moviePopularity;
    private String movieBackdropPath;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
    public MovieData(long id ,double vote , String title , String poster , double popularity , String backDrop){
        movieId = id;
        movieVoteAverage = vote;
        movieTitle = title;
        moviePosterPath = poster;
        moviePopularity = popularity;
        movieBackdropPath = backDrop;
    }

    public long getMovieId(){
        return movieId;
    }

    public double getMovieVotes(){
        return movieVoteAverage;
    }
    public double getMoviePopularity(){
        return moviePopularity;
    }
    public String getMovieTitle(){
        return movieTitle;
    }
    public String getMoviePosterPath(){
        return moviePosterPath;
    }
    public String getMovieBackdropPath(){
        return movieBackdropPath;
    }
    // Parcelling part
    public MovieData(Parcel in){
        movieId= in.readLong();
        movieVoteAverage = in.readDouble();
        movieTitle =  in.readString();
        moviePosterPath = in.readString();
        moviePopularity = in.readDouble();
        movieBackdropPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeDouble(movieVoteAverage);
        dest.writeString(movieTitle);
        dest.writeString(moviePosterPath);
        dest.writeDouble(moviePopularity);
        dest.writeString(movieBackdropPath);

    }
}
