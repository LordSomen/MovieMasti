package com.example.android.moviemasti.fragmentsmanipulation;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.android.moviemasti.MovieAdapter;
import com.example.android.moviemasti.MovieDetailedDataScrollingActivity;
import com.example.android.moviemasti.R;
import com.example.android.moviemasti.datamanipulation.JsonDataParsing;
import com.example.android.moviemasti.datamanipulation.MovieData;
import com.example.android.moviemasti.datamanipulation.Networking;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soumyajit on 14/11/17.
 */

public class PopularMoviesFragment extends Fragment implements MovieAdapter.MovieOnClickItemHandler {

    private final String POPULARITY_URL =
            "https://api.themoviedb.org/3/movie/popular?api_key=532dfe3fbb248c4ecc6f42703334d18e";
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View popularMoviesView = inflater.inflate(R.layout.activity_popular_movies,container,false);
        ButterKnife.bind(this,popularMoviesView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(popularMoviesView.getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(getActivity().getApplicationContext(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(movieAdapter);
        loadMovieData(POPULARITY_URL);
        return popularMoviesView;
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
                movieAdapter.setpopularMoviesData(imageDataResults);
            } else {
                showErrorMessage();
            }

        }

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
    public void onClickItem(MovieData imageData) {
        Intent movieIntent = new Intent(getActivity().getApplicationContext(), MovieDetailedDataScrollingActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("movieIntentData", imageData);
        movieIntent.putExtras(mBundle);
        startActivity(movieIntent);
    }
}
