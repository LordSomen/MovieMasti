package com.example.android.moviemasti.fragmentsmanipulation;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
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
 * Created by soumyajit on 17/11/17.
 */

public class TopRatedMoviesFragment extends Fragment implements MovieAdapter.MovieOnClickItemHandler,
        LoaderManager.LoaderCallbacks<ArrayList<MovieData>> {

    private final String TOP_RATED_URL =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=532dfe3fbb248c4ecc6f42703334d18e";
    private static final String MOVIE_URL = "url";
    private static final int MOVIE_POPULARITY_LOADER = 2400;
    @BindView(R.id.popular_movie_data_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.action_error)
    LinearLayout mErrorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.reload_button)
    Button mReloadButton;
    private MovieAdapter movieAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View popularMoviesView = inflater.inflate(R.layout.activity_popular_movies, container, false);
        ButterKnife.bind(this, popularMoviesView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(popularMoviesView.getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(getActivity().getApplicationContext(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(movieAdapter);
        loadMovieData(TOP_RATED_URL);
        return popularMoviesView;
    }

    public void loadMovieData(String movieApiUrl) {
        Bundle movieBundle = new Bundle();
        movieBundle.putString(MOVIE_URL, movieApiUrl);
        LoaderManager loaderManager = getLoaderManager();
        Loader<ArraySet<MovieData>> loader = loaderManager.getLoader(MOVIE_POPULARITY_LOADER);
        if (loader == null) {
            loaderManager.initLoader(MOVIE_POPULARITY_LOADER, movieBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_POPULARITY_LOADER, movieBundle, this);
        }

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<ArrayList<MovieData>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieData>>(getActivity().getApplicationContext()) {

            @Override
            protected void onStartLoading() {
                mProgressBar.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mErrorLayout.setVisibility(View.INVISIBLE);
                if (args == null) {
                    return;
                } else {
                    forceLoad();
                }
            }

            @Override
            public ArrayList<MovieData> loadInBackground() {
                try {
                    ArrayList<MovieData> movieDataArrayList=null;
                    String apiUrl = args.getString(MOVIE_URL);
                    String jsonMovieResult = Networking.getJSONResponseFromUrl(apiUrl);
                    if (jsonMovieResult != null) {
                        movieDataArrayList = JsonDataParsing.getDataForPopularity(jsonMovieResult);
                    }
                    return movieDataArrayList;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }


        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieData>> loader, ArrayList<MovieData> data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (data != null && data.size() != 0) {
            mErrorLayout.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            movieAdapter.setpopularMoviesData(data);
        } else {
            mErrorLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieData>> loader) {

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

