package com.example.android.moviemasti;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.moviemasti.DataManipulation.MovieData;

public class MovieDetailedDataScrollingActivity extends AppCompatActivity {

    private TextView mContentTextview;
    private AppBarLayout mAppBarLayout;
    private ImageView mBackDropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed_data_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.app_bar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mContentTextview = (TextView) findViewById(R.id.content_text);
        mBackDropImageView = (ImageView)findViewById(R.id.movie_backdrop_image);
        Bundle intentData = getIntent().getExtras();
        MovieData movieData = intentData.getParcelable("movieIntentData");
        if(movieData!=null){
            Long movieId = movieData.getMovieId();
            String movieTitle = movieData.getMovieTitle();
            String moviePosterPath = movieData.getMoviePosterPath();
            String movieBackdropPath = movieData.getMovieBackdropPath();
            mContentTextview.setText(movieTitle+"\n\n");
            mContentTextview.append(moviePosterPath+"\n\n");
            mContentTextview.append(movieBackdropPath+"\n\n");
            loadingImage(moviePosterPath);

        }
    }


   public void loadingImage(String imgUrlPartBackDrop) {
        if(imgUrlPartBackDrop!=null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartBackDrop;
            Glide.with(this).load(imgUrl).into(mBackDropImageView);
        }
    }
}