package com.example.android.moviemasti.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemasti.R;
import com.example.android.moviemasti.database.MovieDataBaseContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soumyajit on 17/11/17.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder> {

    private Context mContext;
    private Cursor favouriteCursor;
    private OnClickFavouriteItemHandler clickFavouriteItemHandler;

    public FavouriteAdapter(Context context, OnClickFavouriteItemHandler onClickFavouriteItemHandler) {
        mContext = context;
        clickFavouriteItemHandler = onClickFavouriteItemHandler;

    }

    @Override
    public FavouriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_items, parent, false));
    }

    @Override
    public void onBindViewHolder(FavouriteHolder holder, int position) {
        int movieIdIndex = favouriteCursor.getColumnIndex(MovieDataBaseContract
                .MovieEntry.COLUMN_MOVIE_ID);
        int movieTitleIndex = favouriteCursor.getColumnIndex(MovieDataBaseContract
                .MovieEntry.COLUMN_MOVIE_TITLE);
        int movieRatingIndex = favouriteCursor.getColumnIndex(MovieDataBaseContract
                .MovieEntry.COLUMN_MOVIE_RATING);
        int moviePosterPathIndex = favouriteCursor.getColumnIndex(MovieDataBaseContract
                .MovieEntry.COLUMN_MOVIE_POSTER_PATH);
        if (favouriteCursor.moveToPosition(position)) {
            Long movieId = favouriteCursor.getLong(movieIdIndex);
            String movieTitle = favouriteCursor.getString(movieTitleIndex);
            float movieRating = favouriteCursor.getFloat(movieRatingIndex);
            String moviePosterPath = favouriteCursor.getString(moviePosterPathIndex);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + moviePosterPath)
                    .placeholder(R.drawable.placeholder3)
                    .error(R.drawable.placeholder3)
                    .into(holder.mImageView);
            holder.ratingTextView.setText(String.valueOf(movieRating) + "/10");
        }
    }

    @Override
    public int getItemCount() {
        if (favouriteCursor == null)
            return 0;
        else
            return favouriteCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        favouriteCursor = cursor;
        notifyDataSetChanged();
    }

    public interface OnClickFavouriteItemHandler {
        void onFavoutiteItemClick(Cursor cursor);
    }

    public class FavouriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_items)
        ImageView mImageView;
        @BindView(R.id.movie_rating)
        TextView ratingTextView;

        public FavouriteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            favouriteCursor.moveToPosition(getAdapterPosition());
            clickFavouriteItemHandler.onFavoutiteItemClick(favouriteCursor);
        }
    }
}
