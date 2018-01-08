package com.albert.popularmoviesredux;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Albert on 10/15/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    final String IMG_URL = "http://image.tmdb.org/t/p/w185/";
    MovieInfo[] mMovieInfos;
    private MovieOnClickListener mMovieOnClickListener;

    public MovieAdapter(MovieOnClickListener movieOnClickListener, MovieInfo[] mMovieInfos) {
        mMovieOnClickListener = movieOnClickListener;
        this.mMovieInfos = mMovieInfos;
    }

    public interface MovieOnClickListener {
        void onClick(MovieInfo movieInfo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieInfo moviePosition = mMovieInfos[adapterPosition];
            mMovieOnClickListener.onClick(moviePosition);
        }
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {

        Glide.with(holder.moviePoster.getContext())
                .load(IMG_URL + (mMovieInfos[position].getPosterPath()))
                .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
//        if (null == mMovieInfos) return 0;
        return mMovieInfos.length;
    }

    public void setMovieData(MovieInfo[] movieInfos) {
        mMovieInfos = movieInfos;
        notifyDataSetChanged();
    }
}
