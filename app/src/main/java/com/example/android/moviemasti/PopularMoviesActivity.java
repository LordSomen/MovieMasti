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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.moviemasti.datamanipulation.JsonDataParsing;
import com.example.android.moviemasti.datamanipulation.MovieData;
import com.example.android.moviemasti.datamanipulation.Networking;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickItemHandler {

    //TODO place your api key in the url

    private final String POPULARITY_URL =
            "https://api.themoviedb.org/3/movie/popular?api_key=_PUT_YOUR_API_KEY_HERE_";
    private final String TOP_RATED_URL =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=_PUT_YOUR_API_KEY_HERE_";

    @BindView(R.id.popular_movie_data_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.action_error)
    LinearLayout mErrorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.reload_button)
    Button mReloadButton;
    private MovieAdapter movieAdapter;
    private ArrayList<MovieData> imageMovieDataResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(getApplicationContext(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(movieAdapter);
        mReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovieData(POPULARITY_URL);
            }
        });

        loadMovieData(POPULARITY_URL);


    }

    @Override
    public void onClickItem(MovieData imageData) {
        Intent movieIntent = new Intent(this, MovieDetailedDataScrollingActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("movieIntentData", imageData);
        movieIntent.putExtras(mBundle);
        startActivity(movieIntent);
    }

    public void loadMovieData(String movieApiUrl) {
        new PopularMoviesDataRequesting().execute(movieApiUrl);
    }

    public void showMovieData() {
        mErrorLayout.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater in = getMenuInflater();
        in.inflate(R.menu.menu_sorting_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        switch (menuItemId) {
            case R.id.menu_sorting_popularity:
                loadMovieData(POPULARITY_URL);
                if (mErrorLayout.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, getString(R.string.sort_toast), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_sorting_rating:
                loadMovieData(TOP_RATED_URL);
                if (mErrorLayout.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, getString(R.string.sort_toast), Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

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
                    if (jsonMovieResult != null) {
                        imageMovieDataResult = JsonDataParsing.getDataForPopularity(jsonMovieResult);
                        return imageMovieDataResult;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return imageMovieDataResult;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> imageDataResults) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (imageDataResults != null) {
                showMovieData();
                movieAdapter.setMovieImageData(imageDataResults);
            } else {
                showErrorMessage();
            }

        }

    }
}
