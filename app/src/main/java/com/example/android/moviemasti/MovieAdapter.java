package com.example.android.moviemasti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by soumyajit on 22/9/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private ArrayList<String> movieImageData;
    private Context mContext;
    private MovieOnClickItemHandler mClickHandler;


    public MovieAdapter(Context context , MovieOnClickItemHandler click){

        mContext = context;
        mClickHandler = click;
    }

    interface MovieOnClickItemHandler{
        void onClickItem(String imageData);
    }
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int itemId = R.layout.movie_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;
        View movieViewObject = inflater.inflate(itemId, viewGroup,attachToParent);
        MovieAdapterViewHolder movieAdapterViewHolder = new MovieAdapterViewHolder(movieViewObject);
         return movieAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        if(movieImageData!=null) {
            String imgUrl = "https://image.tmdb.org/t/p/w500" + movieImageData.get(position);
            Glide.with(mContext).load(imgUrl).into(holder.movieItemImageView);
        }
    }


    @Override
    public int getItemCount() {
        if(movieImageData == null){
            return  0;
        }else{
            return movieImageData.size();
        }

    }

    public void setMovieImageData(ArrayList<String> imageData){
        if(imageData!= null)
            movieImageData = new ArrayList<String>(imageData);
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final public ImageView movieItemImageView ;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            movieItemImageView = (ImageView) itemView.findViewById(R.id.image_items);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
             String imageData = movieImageData.get(getAdapterPosition());
             mClickHandler.onClickItem(imageData);

        }
    }
}
