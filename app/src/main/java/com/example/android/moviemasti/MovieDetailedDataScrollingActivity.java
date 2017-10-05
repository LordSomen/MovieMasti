package com.example.android.moviemasti;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemasti.DataManipulation.MovieData;
import com.squareup.picasso.Picasso;

public class MovieDetailedDataScrollingActivity extends AppCompatActivity {

    private TextView mContentTextView;
    private AppBarLayout mAppBarLayout;
    private ImageView mBackDropImageView;
    private ImageView mPosterImageView;
    private TextView mMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed_data_scrolling);
       //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       //setSupportActionBar(toolbar);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.app_bar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mMovieTitle = (TextView)findViewById(R.id.movie_title);
        mContentTextView = (TextView) findViewById(R.id.content_text);
        mBackDropImageView = (ImageView)findViewById(R.id.movie_backdrop_image);
        mPosterImageView = (ImageView)findViewById(R.id.movie_poster_image);
        Bundle intentData = getIntent().getExtras();
        MovieData movieData = intentData.getParcelable("movieIntentData");
        if(movieData!=null){
            Long movieId = movieData.getMovieId();
            String movieTitle = movieData.getMovieTitle();
            String movieDescription = movieData.getMovieDescription();
            String moviePosterPath = movieData.getMoviePosterPath();
            String movieBackdropPath = movieData.getMovieBackdropPath();
            mMovieTitle.setText(movieTitle);
          //  getSupportActionBar().setWindowTitle()(movieTitle);
            mContentTextView.setText(movieDescription);
            loadingMovieBackDropImage(movieBackdropPath);
            loadingMoviePosterImage(moviePosterPath);
        }

    }

    public void loadingMovieBackDropImage(String imgUrlPartBackDrop) {
        if(imgUrlPartBackDrop!=null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartBackDrop;
            Picasso.with(this).load(imgUrl).into(mBackDropImageView);
        }
    }

    public void loadingMoviePosterImage(String imgUrlPartPoster){
        if(imgUrlPartPoster!=null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartPoster;
            Picasso.with(this).load(imgUrl).into(mPosterImageView);
        }
    }
}