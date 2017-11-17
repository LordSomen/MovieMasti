package com.example.android.moviemasti;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
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
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemasti.adapters.ReviewsAdapter;
import com.example.android.moviemasti.adapters.VideoAdapter;
import com.example.android.moviemasti.datamanipulation.JsonDataParsing;
import com.example.android.moviemasti.datamanipulation.MovieData;
import com.example.android.moviemasti.datamanipulation.Networking;
import com.example.android.moviemasti.pojo.MovieDetails;
import com.example.android.moviemasti.utils.Favourite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailedDataScrollingActivity extends AppCompatActivity implements
        LoaderCallbacks<ArrayList<MovieDetails>>, VideoAdapter.OnVideoClickHandler,
        ReviewsAdapter.OnReviewItemClickHandler {

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
    RecyclerView videoRecyclerView;
    @BindView(R.id.recycler_view_review)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.movie_details_favourite)
    ImageButton mImageButton;
    @BindView(R.id.error_trailers)
    TextView mTrailerErrorTextView;
    @BindView(R.id.error_reviews)
    TextView mReviewErrorTextView;
    private String movieTitle;
    private VideoAdapter videoAdapter;
    private ReviewsAdapter reviewsAdapter;
    private final static int MOVIE_VIDEO_LOADER = 2416;
    private final static int MOVIE_REVIEW_LOADER = 1600;

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
            double popularity = movieData.getMoviePopularity();

            mMovieTitle.setText(movieTitle);
            mContentTextView.setText(movieDescription);
            mMovieReleaseDate.setText(movieReleaseDate);
            mMovieRate.setText(movieRate);
            loadingMovieBackDropImage(movieBackdropPath);
            loadingMoviePosterImage(moviePosterPath);
            setImageButtons(movieId, moviePosterPath, movieTitle, movieRate,popularity,
                    movieBackdropPath,movieDescription,movieReleaseDate);
        }

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);
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
        videoAdapter = new VideoAdapter(getApplicationContext(), this);
        reviewsAdapter = new ReviewsAdapter(getApplicationContext(), this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.VERTICAL, false);
        videoRecyclerView.setLayoutManager(linearLayoutManager1);
        videoRecyclerView.setAdapter(videoAdapter);
        reviewRecyclerView.setLayoutManager(linearLayoutManager2);
        reviewRecyclerView.setAdapter(reviewsAdapter);
        String movieVideoUrl = MOVIE_URL + movieId + "/" + MOVIE_VIDEO_CALL + MOVIE_API;
        String movieReviewUrl = MOVIE_URL + movieId + "/" + MOVIE_REVIEW_CALL + MOVIE_API;
        loadData(movieVideoUrl, MOVIE_VIDEO_LOADER);
        loadData(movieReviewUrl, MOVIE_REVIEW_LOADER);
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

    public void loadData(String url, int LoaderConstant) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_CALL, url);
        LoaderManager loaderManager = getLoaderManager();
        Loader<ArrayList<MovieDetails>> movieDetailsLoader = loaderManager
                .getLoader(LoaderConstant);
        if (movieDetailsLoader == null)
            loaderManager.initLoader(LoaderConstant, bundle, this);
        else
            loaderManager.restartLoader(LoaderConstant, bundle, this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<ArrayList<MovieDetails>> onCreateLoader(final int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieDetails>>(this) {
            @Override
            protected void onStartLoading() {
                switch (id) {
                    case MOVIE_VIDEO_LOADER:
                        mTrailerErrorTextView.setVisibility(View.GONE);
                        videoRecyclerView.setVisibility(View.VISIBLE);
                        break;
                    case MOVIE_REVIEW_LOADER:
                        mReviewErrorTextView.setVisibility(View.GONE);
                        reviewRecyclerView.setVisibility(View.VISIBLE);
                }
                if (isStarted())
                    forceLoad();
            }

            @Override
            public ArrayList<MovieDetails> loadInBackground() {
                String apiUrl = args.getString(MOVIE_CALL);
                Log.i("MovieDetails", apiUrl);
                ArrayList<MovieDetails> movieDetailsArrayList = null;
                try {

                    String jsonMovieResult = Networking.getJSONResponseFromUrl(apiUrl);
                    if (id == MOVIE_VIDEO_LOADER)
                        movieDetailsArrayList = JsonDataParsing.getVideoData(jsonMovieResult);
                    else if (id == MOVIE_REVIEW_LOADER)
                        movieDetailsArrayList = JsonDataParsing.getReviewData(jsonMovieResult);
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
            if (loader.getId() == MOVIE_VIDEO_LOADER) {
                videoAdapter.setArrayList(data);
                mTrailerErrorTextView.setVisibility(View.GONE);
            } else if (loader.getId() == MOVIE_REVIEW_LOADER) {
                reviewsAdapter.setArrayListReview(data);
                mReviewErrorTextView.setVisibility(View.GONE);
            }
        } else {
            if (loader.getId() == MOVIE_VIDEO_LOADER) {
                mTrailerErrorTextView.setVisibility(View.VISIBLE);
                videoRecyclerView.setVisibility(View.GONE);
            } else if (loader.getId() == MOVIE_REVIEW_LOADER) {
                mReviewErrorTextView.setVisibility(View.VISIBLE);
                reviewRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieDetails>> loader) {

    }


    @Override
    public void onVideoClick(String key, Context context) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                ("https://www.youtube.com/watch?v=" + key));
        if (webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        }
    }

    private void setImageButtons(final Long movieId, final String posterPath, final String movieTitle,
                                 final String movieRate, final double popularity, final String backdropPath ,
                                 final String description, final String releaseDate) {
        if (movieId == null) {
            return;
        }
        if (Favourite.isMovieFav(MovieDetailedDataScrollingActivity.this, movieId)) {
            mImageButton.setTag(Favourite.TAG_FAV);
            mImageButton.setImageResource(R.drawable.ic_favourite_pressed);
        } else {
            mImageButton.setTag(Favourite.TAG_NOT_FAV);
            mImageButton.setImageResource(R.drawable.ic_favourites_not_pressed);
        }
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if ((int) mImageButton.getTag() == Favourite.TAG_FAV) {
                    Favourite.removeMovieFromFav(MovieDetailedDataScrollingActivity.this, movieId);
                    mImageButton.setTag(Favourite.TAG_NOT_FAV);
                    mImageButton.setImageResource(R.drawable.ic_favourites_not_pressed);
                    Log.i("TAG_NOT_FAV", "movieID");
                } else {
                    Favourite.addMovieToFav(MovieDetailedDataScrollingActivity.this,
                            movieId, posterPath, movieTitle, movieRate,popularity,backdropPath,
                            description,releaseDate);
                    mImageButton.setTag(Favourite.TAG_FAV);
                    mImageButton.setImageResource(R.drawable.ic_favourite_pressed);
                }
            }
        });
    }

    @Override
    public void onClickReviewItem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}
