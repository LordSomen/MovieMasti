package com.example.android.moviemasti.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by soumyajit on 16/11/17.
 */

public class MovieDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favourite_movie.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String MOVIE_SQL_CREATE = " CREATE TABLE " + MovieDataBaseContract.MovieEntry.TABLE_NAME + " ( " +
                MovieDataBaseContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID + " REAL NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_POPULARITY + " REAL NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL, " +
                MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                " UNIQUE ( " + MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_ID + " ) ON CONFLICT REPLACE ) ;";

        db.execSQL(MOVIE_SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
