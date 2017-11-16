package com.example.android.moviemasti.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.moviemasti.database.MovieDataBaseContract;
import com.example.android.moviemasti.database.MovieDataBaseHelper;

/**
 * Created by soumyajit on 17/11/17.
 */

public class Favourite {

    public static final int TAG_FAV = 1;
    public static final int TAG_NOT_FAV = 0;

    public static boolean isMovieFav(Context context, Long movieId) {
        if (movieId == null) return false;
        MovieDataBaseHelper databaseHelper = new MovieDataBaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        boolean isMovieFav;
        Cursor cursor = database.query(MovieDataBaseContract.MovieEntry.TABLE_NAME, null,
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId,
                null, null, null, null);
        if (cursor.getCount() == 1)
            isMovieFav = true;
        else
            isMovieFav = false;

        cursor.close();
        database.close();
        return isMovieFav;
    }

    public static void addMovieToFav(Context context, Long movieId, String posterPath, String name,String rating) {
        if (movieId == null) return;
        MovieDataBaseHelper databaseHelper = new MovieDataBaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        if (!isMovieFav(context, movieId)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, posterPath);
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_TITLE, name);
            contentValues.put(MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_RATING,rating);

            database.insert(MovieDataBaseContract.MovieEntry.TABLE_NAME, null, contentValues);
        }
        database.close();
    }

    public static void removeMovieFromFav(Context context, Long movieId) {
        if (movieId == null) return;
        MovieDataBaseHelper databaseHelper = new MovieDataBaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        if (isMovieFav(context, movieId)) {
            database.delete(MovieDataBaseContract.MovieEntry.TABLE_NAME,
                    MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId,
                    null);
        }
        database.close();
    }
}
