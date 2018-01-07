package com.albert.popularmoviesredux;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Albert on 10/15/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    final String IMG_URL = "http://image.tmdb.org/t/p/";
    MovieInfo[] mMovieInfos;

    public MovieAdapter() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView movieReleaseDate;
        ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);

            movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.tv_movie_release_date);
            moviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
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

//        holder.movieTitle.setText(mMovieInfos[position].getOriginalTitle());
//        holder.movieReleaseDate.setText(mMovieInfos[position].getReleaseDate());

        Glide.with(holder.moviePoster.getContext())
                .load(IMG_URL + "w185/" + (mMovieInfos[position].getPosterPath()))
                .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        if (null == mMovieInfos) return 0;
        return mMovieInfos.length;
    }

    public void setMovieData(MovieInfo[] movieInfos) {
        mMovieInfos = movieInfos;
        notifyDataSetChanged();
    }
}
