package com.example.android.moviemasti.menusmanipulation;

import android.util.Log;

import com.example.android.moviemasti.datamanipulation.MovieData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by soumyajit on 28/9/17.
 */

//still no use but will be using in future......

public class SortingMovieData {

    public static ArrayList<MovieData> sortAccordingToPopularity(ArrayList<MovieData> movieDataArrayList) {
        Collections.sort(movieDataArrayList, MovieData.popularity);
        for (MovieData b : movieDataArrayList)
            Log.i("SortingMovieData.class", "output:" + b.getMoviePopularity() + "\n\n");
        return movieDataArrayList;
    }

    public static ArrayList<MovieData> sortAccordingToRating(ArrayList<MovieData> movieDataArrayList) {
        Collections.sort(movieDataArrayList, MovieData.rating);
        for (MovieData b : movieDataArrayList)
            Log.i("SortingMovieData.class", "output:" + b.getMoviePopularity() + "\n\n");
        return movieDataArrayList;
    }
}
