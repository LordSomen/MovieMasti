package com.example.android.moviemasti;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.moviemasti.DataManipulation.Networking;

import java.io.IOException;

public class PopularMoviesActivity extends AppCompatActivity {

    private final String POPULARITY_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=532dfe3fbb248c4ecc6f42703334d18e&language=en&sort_by=popularity.desc&include_adult=false&include_video=false";
    private TextView mDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        mDataTextView = (TextView) findViewById(R.id.popular_movie_data);
        new PopularMoviesDataRequesting().execute(POPULARITY_URL);
    }

    class PopularMoviesDataRequesting extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... popularMovieUrls) {
            String jsonMovieResult = null;
            try {
                if (popularMovieUrls != null) {
                    jsonMovieResult = Networking.getJSONResponseFromUrl(popularMovieUrls[0]);
                    return jsonMovieResult;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonMovieResult;
        }

        @Override
        protected void onPostExecute(String jsonResult) {
            if (jsonResult != null && !jsonResult.equals("")) {
                mDataTextView.setText(jsonResult);
            } else
                mDataTextView.setText("Some error occcurs");
        }
    }

}
