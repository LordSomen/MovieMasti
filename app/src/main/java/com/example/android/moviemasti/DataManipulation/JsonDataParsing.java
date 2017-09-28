package com.example.android.moviemasti.DataManipulation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by soumyajit on 22/9/17.
 */

public class JsonDataParsing {

    public static ArrayList<MovieData> getDataForPopularity(String jsonPopularMovieData) throws JSONException{
        ArrayList<MovieData> imageDataArray = new ArrayList<>();
        JSONObject popularMovieJson = new JSONObject(jsonPopularMovieData);
        JSONArray movieList = popularMovieJson.getJSONArray("results");
        for(int i=0 ; i<movieList.length() ; i++){
            JSONObject movieListItem = movieList.getJSONObject(i);
            String moviePosterPath = movieListItem.getString("poster_path");
            int movieId = movieListItem.getInt("id");
            double movieVoteAverage = movieListItem.getDouble("vote_average");
            double moviePopularity = movieListItem.getDouble("popularity");
            String movieTitle = movieListItem.getString("title");
            String movieBackDropPath = movieListItem.getString("backdrop_path");
            imageDataArray.add(new MovieData(movieId,movieVoteAverage,movieTitle,moviePosterPath,moviePopularity,movieBackDropPath));
        }
        return imageDataArray;
    }

}
