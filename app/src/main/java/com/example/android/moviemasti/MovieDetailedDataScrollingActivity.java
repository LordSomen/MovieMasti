package com.example.android.moviemasti;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviemasti.adapters.VideoAdapter;
import com.example.android.moviemasti.datamanipulation.JsonDataParsing;
import com.example.android.moviemasti.datamanipulation.MovieData;
import com.example.android.moviemasti.datamanipulation.Networking;
import com.example.android.moviemasti.pojo.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailedDataScrollingActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<MovieDetails>>,VideoAdapter.OnVideoClickHandler {

    @SuppressWarnings("FieldCanBeLocal")
    @BindView(R.id.content_text)
    TextView mContentTextView;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.movie_backdrop_image)
    ImageView mBackDropImageView;
    @BindView(R.id.movie_poster_image)
    ImageView mPosterImageView;
    @BindView(R.id.movie_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_details_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.movie_details_rate)
    TextView mMovieRate;
    @BindView(R.id.recycler_view_video)
    RecyclerView detailsRecyclerView;
    private String movieTitle;
    private VideoAdapter videoAdapter;
    private final static int MOVIE_DETAILS_LOADER = 2416;
    private final static String MOVIE_CALL = "movies";
    private final static String MOVIE_VIDEO_CALL = "videos";
    private final static String MOVIE_REVIEW_CALL = "reviews";
    private final static String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String MOVIE_API = "?api_key=532dfe3fbb248c4ecc6f42703334d18e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed_data_scrolling);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        Bundle intentData = getIntent().getExtras();
        assert intentData != null;
        MovieData movieData = intentData.getParcelable("movieIntentData");
        long movieId = 0;
        if (movieData != null) {
            movieId = movieData.getMovieId();
            movieTitle = movieData.getMovieTitle();
            String movieDescription = movieData.getMovieDescription();
            String moviePosterPath = movieData.getMoviePosterPath();
            String movieBackdropPath = movieData.getMovieBackdropPath();
            String movieReleaseDate = movieData.getMovieReleaseDate();
            String movieRate = movieData.getMovieVotes() + "/10";
            mMovieTitle.setText(movieTitle);
            mContentTextView.setText(movieDescription);
            mMovieReleaseDate.setText(movieReleaseDate);
            mMovieRate.setText(movieRate);
            loadingMovieBackDropImage(movieBackdropPath);
            loadingMoviePosterImage(moviePosterPath);
        }

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(movieTitle);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
        videoAdapter = new VideoAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                ,LinearLayoutManager.HORIZONTAL,false);
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        detailsRecyclerView.setAdapter(videoAdapter);
        String movieVideoUrl = MOVIE_URL + movieId + "/" + MOVIE_VIDEO_CALL + MOVIE_API;
        loadData(movieVideoUrl);
    }

    public void loadingMovieBackDropImage(String imgUrlPartBackDrop) {
        if (imgUrlPartBackDrop != null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartBackDrop;
            Picasso.with(this).load(imgUrl)
                    .placeholder(R.drawable.placeholder3)
                    .into(mBackDropImageView);
        }
    }

    public void loadingMoviePosterImage(String imgUrlPartPoster) {
        if (imgUrlPartPoster != null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartPoster;
            Picasso.with(this).load(imgUrl)
                    .placeholder(R.drawable.placeholder3)
                    .into(mPosterImageView);
        }
    }

    public void loadData(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_CALL, url);
        LoaderManager loaderManager = getLoaderManager();
        Loader<ArrayList<MovieDetails>> movieDetailsLoader = loaderManager
                .getLoader(MOVIE_DETAILS_LOADER);
        if (movieDetailsLoader == null)
            loaderManager.initLoader(MOVIE_DETAILS_LOADER, bundle, this);
        else
            loaderManager.restartLoader(MOVIE_DETAILS_LOADER, bundle, this);
    }

    @Override
    public Loader<ArrayList<MovieDetails>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieDetails>>(this) {
            @Override
            protected void onStartLoading() {
               if(isStarted())
                    forceLoad();
            }

            @Override
            public ArrayList<MovieDetails> loadInBackground() {
                String apiUrl = args.getString(MOVIE_CALL);
                Log.i("MovieDetails",apiUrl);
                ArrayList<MovieDetails> movieDetailsArrayList = null;
                try {
                    String jsonMovieResult = Networking.getJSONResponseFromUrl(apiUrl);
                    movieDetailsArrayList = JsonDataParsing.getVideoData(jsonMovieResult);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return movieDetailsArrayList;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieDetails>> loader, ArrayList<MovieDetails> data) {
        if (data != null && data.size() != 0) {
            videoAdapter.setArrayList(data);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieDetails>> loader) {

    }


    @Override
    public void onVideoClick(String key, Context context) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse
                ("https://www.youtube.com/watch?v="+key));
        /*try{
            startActivity(appIntent);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
           startActivity(webIntent);
        }*/
        if (webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        }

        // startActivity(webIntent);

    }
}