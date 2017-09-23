package com.example.android.moviemasti;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviemasti.DataManipulation.JsonDataParsing;
import com.example.android.moviemasti.DataManipulation.Networking;

import org.json.JSONException;

import java.util.ArrayList;

public class PopularMoviesActivity extends AppCompatActivity {

    private final String POPULARITY_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=532dfe3fbb248c4ecc6f42703334d18e&language=en&sort_by=popularity.desc&include_adult=false&include_video=false";
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    //TODO make sure the app didn't crash when internet isn't available
//TODO add progress bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        mRecyclerView = (RecyclerView) findViewById(R.id.popular_movie_data_rv);
        mErrorTextView = (TextView) findViewById(R.id.action_error);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this ,GridLayoutManager.DEFAULT_SPAN_COUNT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setLayoutManager(gridLayoutManager);
        //mRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getApplicationContext());
        loadMovieData();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(movieAdapter);

    }

    class PopularMoviesDataRequesting extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(String... popularMovieUrls) {
            ArrayList<String> imageMovieDataResult = null;
            try {
                if (popularMovieUrls != null) {
                    String jsonMovieResult = Networking.getJSONResponseFromUrl(popularMovieUrls[0]);
                    if(jsonMovieResult!=null) {
                        imageMovieDataResult = JsonDataParsing.getDataForPopularity(jsonMovieResult);
                        return imageMovieDataResult;
                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return imageMovieDataResult;
        }

        @Override
        protected void onPostExecute(ArrayList<String> imageDataResults) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (imageDataResults != null ) {
                showMovieData();
                movieAdapter.setMovieImageData(imageDataResults);
            } else {
                showErrorMessage();
            }

        }

    }

    public void loadMovieData() {
        showMovieData();
        new PopularMoviesDataRequesting().execute(POPULARITY_URL);
    }

    public void showMovieData() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }
}
