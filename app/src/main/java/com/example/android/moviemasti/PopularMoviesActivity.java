package com.example.android.moviemasti;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviemasti.DataManipulation.JsonDataParsing;
import com.example.android.moviemasti.DataManipulation.MovieData;
import com.example.android.moviemasti.DataManipulation.Networking;
import com.example.android.moviemasti.MenusManipulation.SortingMovieData;

import org.json.JSONException;

import java.util.ArrayList;

public class PopularMoviesActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickItemHandler {

    //TODO place your api key in the url
    private final String POPULARITY_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=Replace with your own api key&language=en&sort_by=popularity.desc&include_adult=false&include_video=false";
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;
    private Toast mToast ;
    private  ArrayList<MovieData> imageMovieDataResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.popular_movie_data_rv);
        mErrorTextView = (TextView) findViewById(R.id.action_error);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this ,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(getApplicationContext() ,this);
        loadMovieData();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(movieAdapter);

    }

    @Override
    public void onClickItem(MovieData imageData) {
        Intent movieIntent = new Intent(this,MovieDetailedDataScrollingActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("movieIntentData",imageData);
        movieIntent.putExtras(mBundle);
        startActivity(movieIntent);
    }


    private class PopularMoviesDataRequesting extends AsyncTask<String, Void, ArrayList<MovieData>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MovieData> doInBackground(String... popularMovieUrls) {

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
        protected void onPostExecute(ArrayList<MovieData> imageDataResults) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater in = getMenuInflater();
        in.inflate(R.menu.menu_sorting_movies,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int menuItemId = menuItem.getItemId();
        switch (menuItemId){
            case R.id.menu_sorting_popularity:
               imageMovieDataResult = SortingMovieData.sortAccordingToPopularity(imageMovieDataResult);
                movieAdapter.setMovieImageData(imageMovieDataResult);
                return true;
            case R.id.menu_sorting_rating:
                imageMovieDataResult = SortingMovieData.sortAccordingToRating(imageMovieDataResult);
                movieAdapter.setMovieImageData(imageMovieDataResult);
                return true;
            default:
               return super.onOptionsItemSelected(menuItem);
        }

    }
}
