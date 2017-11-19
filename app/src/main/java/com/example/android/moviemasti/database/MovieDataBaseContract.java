package com.example.android.moviemasti.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by soumyajit on 16/11/17.
 */

public class MovieDataBaseContract {

    public static final String MOVIE_AUTHORITY = "com.example.android.moviemasti";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + MOVIE_AUTHORITY);
    public static final String PATH_TASKS = "favourite_movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TASKS).build();
        public static final String TABLE_NAME = "favourite_movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";
        public static final String COLUMN_MOVIE_RATING = "movie_rating";
        public static final String COLUMN_MOVIE_POPULARITY = "movie_popularity";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        public static final String COLUMN_MOVIE_DESCRIPTION = "movie_description";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";

    }
}
