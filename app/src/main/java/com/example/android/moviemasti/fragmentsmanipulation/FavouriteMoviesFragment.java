package com.example.android.moviemasti.fragmentsmanipulation;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviemasti.R;
import com.example.android.moviemasti.adapters.FavouriteAdapter;
import com.example.android.moviemasti.database.MovieDataBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soumyajit on 17/11/17.
 */

public class FavouriteMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.favourite_movie_data_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.favourite_action_error)
    TextView mErrorTextView;
    @BindView(R.id.favourite_progress_bar)
    ProgressBar mProgressbar;
    public static final int FAV_MOVIE_LOADER = 1216;
    private FavouriteAdapter favouriteAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favourite_fragment,container,false);
        ButterKnife.bind(this,view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        favouriteAdapter = new FavouriteAdapter(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(favouriteAdapter);
        getLoaderManager().initLoader(FAV_MOVIE_LOADER,null,this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(FAV_MOVIE_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(getActivity().getApplicationContext()) {
            Cursor mCursor = null;
            @Override
            protected void onStartLoading() {
                mProgressbar.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                if(mCursor != null)
                    deliverResult(mCursor);
                else
                    forceLoad();
            }

            @Override
            public Cursor loadInBackground() {

                try{
                    return getContext().getContentResolver().query(MovieDataBaseContract.MovieEntry.CONTENT_URI,
                            null,null,null,
                            MovieDataBaseContract.MovieEntry.COLUMN_MOVIE_RATING);
                }catch (Exception e){
                    Log.i("favourite","Not succesfully loaderd the data",e);
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                super.deliverResult(data);
                mCursor = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProgressbar.setVisibility(View.INVISIBLE);
        if(data != null && data.getCount()!=0){
            if(loader.getId() == FAV_MOVIE_LOADER)
                favouriteAdapter.swapCursor(data);
        }else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
