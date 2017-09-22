package com.example.android.moviemasti.DataManipulation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by soumyajit on 22/9/17.
 */

public class JsonDataParsing {

    public static ArrayList<String> getDataForPopularity(String jsonPopularMovieData) throws JSONException{
        ArrayList<String> imageDataArray = new ArrayList<>();
        JSONObject popularMovieJson = new JSONObject(jsonPopularMovieData);
        JSONArray movieList = popularMovieJson.getJSONArray("results");
        for(int i=0 ; i<movieList.length() ; i++){
            JSONObject movieListItem = movieList.getJSONObject(i);
            String image_loc = movieListItem.getString("poster_path");
            imageDataArray.add(image_loc);
        }
        return imageDataArray;
    }


}
