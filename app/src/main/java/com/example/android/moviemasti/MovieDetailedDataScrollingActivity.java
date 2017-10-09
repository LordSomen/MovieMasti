package com.example.android.moviemasti;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemasti.DataManipulation.MovieData;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class MovieDetailedDataScrollingActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    @BindView(R.id.content_text) TextView mContentTextView;
    @BindView(R.id.app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.movie_backdrop_image) ImageView mBackDropImageView;
    @BindView(R.id.movie_poster_image) ImageView mPosterImageView;
    @BindView(R.id.movie_title) TextView mMovieTitle;
    @BindView(R.id.movie_details_release_date)  TextView mMovieReleaseDate;
    @BindView(R.id.movie_details_rate)  TextView mMovieRate;
    private String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed_data_scrolling);
        ButterKnife.bind(this);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        setTitle("");
        Bundle intentData = getIntent().getExtras();
        MovieData movieData = intentData.getParcelable("movieIntentData");
        if(movieData!=null){
            Long movieId = movieData.getMovieId();
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
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    public void loadingMovieBackDropImage(String imgUrlPartBackDrop) {
        if(imgUrlPartBackDrop!=null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartBackDrop;
            Picasso.with(this).load(imgUrl).placeholder(R.drawable.placeholder3).into(mBackDropImageView);
        }
    }

    public void loadingMoviePosterImage(String imgUrlPartPoster){
        if(imgUrlPartPoster!=null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + imgUrlPartPoster;
            Picasso.with(this).load(imgUrl).placeholder(R.drawable.placeholder3).into(mPosterImageView);
        }
    }

}