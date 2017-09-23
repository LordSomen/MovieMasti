package com.example.android.moviemasti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by soumyajit on 22/9/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private ArrayList<String> movieImageData;
    private Context mContext;
    public MovieAdapter(Context context){

        mContext = context;
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

            String imgUrl = "https://image.tmdb.org/t/p/w500" + movieImageData.get(position);
            holder.movieItemImageView.setText(imgUrl);
            // Glide.with(mContext).load(imgUrl).into(holder.movieItemImageView);

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
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        final public TextView movieItemImageView ;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            movieItemImageView = (TextView)itemView.findViewById(R.id.image_items);
        }
    }
}
