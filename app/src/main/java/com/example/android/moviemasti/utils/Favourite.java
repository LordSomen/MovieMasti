package com.example.android.moviemasti.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.android.moviemasti.database.MovieDataBaseContract;

/**
 * Created by soumyajit on 17/11/17.
 */

public class Favourite {

    public static final int TAG_FAV = 1;
    public static final int TAG_NOT_FAV = 0;

    public static boolean isMovieFav(Context context, Long movieId) {
        if (movieId == null) return false;

        boolean isMovieFav;
        Cursor cursor = context.getContentResolver().query(MovieDataBaseContract.MovieEntry.CONTENT_URI, null,
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId,
                null, null);

        assert cursor != null;
        if (cursor.getCount() == 1)
            isMovieFav = true;
        else
            isMovieFav = false;
        cursor.close();
        return isMovieFav;
    }

    public static void addMovieToFav(Context context, Long movieId, String posterPath, String name,String rating) {
        if (movieId == null) return;

        if (!isMovieFav(context, movieId)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, posterPath);
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_TITLE, name);
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_RATING,rating);

           context.getContentResolver().insert(MovieDataBaseContract.MovieEntry.CONTENT_URI,contentValues);
        }
    }

    public static void removeMovieFromFav(Context context, Long movieId) {
        if (movieId == null) return;
        if (isMovieFav(context, movieId)) {
           int deleted =  context.getContentResolver().delete(MovieDataBaseContract.MovieEntry.CONTENT_URI,
                   MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID +" = "+movieId,null);
        }

    }
}
