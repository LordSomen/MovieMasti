package com.example.android.moviemasti.MenusManipulation;

import android.util.Log;

import com.example.android.moviemasti.DataManipulation.MovieData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by soumyajit on 28/9/17.
 */

public class SortingMovieData {

        public static ArrayList<MovieData> sortAccordingToPopularity(ArrayList<MovieData> movieDataArrayList){
            Collections.sort(movieDataArrayList,MovieData.popularity);
            for(MovieData b : movieDataArrayList)
                Log.i("SortingMovieData.class","output:"+b.getMoviePopularity()+"\n\n");
            return movieDataArrayList;
        }
    public static ArrayList<MovieData> sortAccordingToRating(ArrayList<MovieData> movieDataArrayList){
        Collections.sort(movieDataArrayList,MovieData.rating);
        for(MovieData b : movieDataArrayList)
            Log.i("SortingMovieData.class","output:"+b.getMoviePopularity()+"\n\n");
        return movieDataArrayList;
    }
}
