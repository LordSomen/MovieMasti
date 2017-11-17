package com.example.android.moviemasti.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviemasti.R;
import com.example.android.moviemasti.pojo.MovieDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soumyajit on 16/11/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>{

    private Context mContext;
    private ArrayList<MovieDetails> reviewArrayList;
    public ReviewsAdapter(Context context){
        mContext = context;
    }
    public OnReviewItemClickHandler reviewItemClickHandler;
    public interface OnReviewItemClickHandler{
        void onClickReviewItem(String url);
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ReviewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        MovieDetails movieDetails = reviewArrayList.get(position);
        if(movieDetails != null){
            String author = movieDetails.getReviewAuthor();
            String content = movieDetails.getReviewContent();
            String url = movieDetails.getReviewUrl();
            holder.mAuthorTextView.setText(author);
            holder.mReviewCOntentTextView.setText(content);
        }
    }

    @Override
    public int getItemCount() {
        if(reviewArrayList == null)
            return 0;
        return reviewArrayList.size();
    }

    public void setArrayListReview(ArrayList<MovieDetails> data) {
        if(data !=null){
            reviewArrayList = data;
            notifyDataSetChanged();
        }
    }

    public class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.author_name)
        public TextView mAuthorTextView;
        @BindView(R.id.review_content)
        public TextView mReviewCOntentTextView;

        public ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieDetails movieDetails = reviewArrayList.get(getAdapterPosition());
            reviewItemClickHandler.onClickReviewItem(movieDetails.getReviewUrl());
        }
    }
}
