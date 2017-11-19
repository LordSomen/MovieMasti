package com.example.android.moviemasti.datamanipulation;

import com.example.android.moviemasti.pojo.MovieData;
import com.example.android.moviemasti.pojo.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by soumyajit on 22/9/17.
 */

public class JsonDataParsing {

    public static ArrayList<MovieData> getDataForPopularity(String jsonPopularMovieData) throws JSONException {
        ArrayList<MovieData> popularMoviesDataArray = new ArrayList<>();
        JSONObject popularMovieJson = new JSONObject(jsonPopularMovieData);
        JSONArray movieList = popularMovieJson.getJSONArray("results");
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject movieListItem = movieList.getJSONObject(i);
            String moviePosterPath = movieListItem.getString("poster_path");
            int movieId = movieListItem.getInt("id");
            double movieVoteAverage = movieListItem.getDouble("vote_average");
            double moviePopularity = movieListItem.getDouble("popularity");
            String movieTitle = movieListItem.getString("title");
            String movieBackDropPath = movieListItem.getString("backdrop_path");
            String movieDescription = movieListItem.getString("overview");
            String movieReleaseDate = movieListItem.getString("release_date");
            popularMoviesDataArray.add(new MovieData(movieId, movieVoteAverage, movieTitle, moviePosterPath, moviePopularity, movieBackDropPath, movieDescription, movieReleaseDate));
        }
        return popularMoviesDataArray;
    }

    public static ArrayList<MovieDetails> getVideoData(String jsonVideoData) throws JSONException {

        ArrayList<MovieDetails> movieVideoArray = new ArrayList<>();
        JSONObject videoDataJson = new JSONObject(jsonVideoData);
        JSONArray videoList = videoDataJson.getJSONArray("results");
        for (int i = 0; i < videoList.length(); i++) {
            JSONObject videoListItem = videoList.getJSONObject(i);
            String videoKey = videoListItem.getString("key");
            String videoName = videoListItem.getString("name");
            String videoSite = videoListItem.getString("site");
            movieVideoArray.add(new MovieDetails(videoKey, videoName, videoSite, null, null, null));
        }
        return movieVideoArray;
    }

    public static ArrayList<MovieDetails> getReviewData(String jsonVideoData) throws JSONException {

        ArrayList<MovieDetails> movieReviewArray = new ArrayList<>();
        JSONObject videoDataJson = new JSONObject(jsonVideoData);
        JSONArray videoList = videoDataJson.getJSONArray("results");
        for (int i = 0; i < videoList.length(); i++) {
            JSONObject videoListItem = videoList.getJSONObject(i);
            String author = videoListItem.getString("author");
            String content = videoListItem.getString("content");
            String url = videoListItem.getString("url");
            movieReviewArray.add(new MovieDetails(null, null, null, author, content, url));
        }
        return movieReviewArray;
    }


}
