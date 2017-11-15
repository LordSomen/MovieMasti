package com.example.android.moviemasti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemasti.datamanipulation.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soumyajit on 22/9/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<MovieData> popularMoviesData;
    private Context mContext;
    private MovieOnClickItemHandler mClickHandler;

   public interface MovieOnClickItemHandler {
        void onClickItem(MovieData imageData);
    }

    public MovieAdapter(Context context, MovieOnClickItemHandler click) {

        mContext = context;
        mClickHandler = click;
        setHasStableIds(true);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int itemId = R.layout.movie_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;
        View movieViewObject = inflater.inflate(itemId, viewGroup, attachToParent);
        return new MovieAdapterViewHolder(movieViewObject);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        if (popularMoviesData != null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + popularMoviesData.get(position).getMoviePosterPath();
            Picasso.with(mContext).load(imgUrl)
                    .placeholder(R.drawable.placeholder3)
                    .error(R.drawable.placeholder3)
                    .into(holder.movieItemImageView);
            double ratings = popularMoviesData.get(position).getMovieVotes();
            String rateText = String.valueOf(ratings) + "/10";
            holder.mMovieRatings.setText(String.valueOf(rateText));
        }
    }

    @Override
    public int getItemCount() {
        if (popularMoviesData == null) {
            return 0;
        } else {
            return popularMoviesData.size();
        }
    }

    public void setpopularMoviesData(ArrayList<MovieData> imageData) {
        if (imageData != null)
            popularMoviesData = imageData;
        notifyDataSetChanged();
    }



    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_items)
        public ImageView movieItemImageView;
        @BindView(R.id.movie_rating)
        public TextView mMovieRatings;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieData imageData = popularMoviesData.get(getAdapterPosition());
            mClickHandler.onClickItem(imageData);

        }
    }
}
