package com.example.android.moviemasti.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviemasti.R;
import com.example.android.moviemasti.pojo.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soumyajit on 15/11/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    public static final String YOUTUBE_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_THUMBNAIL_IMAGE_QUALITY = "/hqdefault.jpg";
    private Context mContext;
    private ArrayList<MovieDetails> videoDetailsArrayList;
    private OnVideoClickHandler videoClickHandler;

    public VideoAdapter(Context context, OnVideoClickHandler click) {
        mContext = context;
        videoClickHandler = click;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        return new VideoHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        MovieDetails movieDetails = videoDetailsArrayList.get(position);
        if (movieDetails != null) {
            String imgUrl = YOUTUBE_THUMBNAIL_BASE_URL + movieDetails.getShowVideoKey()
                    + YOUTUBE_THUMBNAIL_IMAGE_QUALITY;
            Picasso.with(mContext).load(imgUrl)
                    .placeholder(R.drawable.placeholder3)
                    .error(R.drawable.placeholder3)
                    .into(holder.videoImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (null == videoDetailsArrayList)
            return 0;
        else
            return videoDetailsArrayList.size();
    }

    public void setArrayList(ArrayList<MovieDetails> arrayList) {
        if (arrayList != null) {
            videoDetailsArrayList = arrayList;
            notifyDataSetChanged();
        }
    }

    public interface OnVideoClickHandler {
        void onVideoClick(String key, Context context);
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.video_image_view)
        ImageView videoImageView;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieDetails movieDetails = videoDetailsArrayList.get(getAdapterPosition());
            videoClickHandler.onVideoClick(movieDetails.getShowVideoKey(), mContext);
        }

    }
}