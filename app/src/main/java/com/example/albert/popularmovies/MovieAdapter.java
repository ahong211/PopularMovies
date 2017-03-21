package com.example.albert.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Albert on 3/16/17.
 */

public class MovieAdapter extends ArrayAdapter {

    Activity mContext;
    // ArrayList<MovieInfo> poster = new ArrayList<MovieInfo>();
    private MovieInfo url;


    public MovieAdapter(Activity context, ArrayList<MovieInfo> poster) {
        super(context, 0, poster);

        this.mContext = context;
       // this.poster = poster;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_posters, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_poster_textview);

        url = (MovieInfo) getItem(position);
        String temp = url.posterURL;

        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w185" + temp)
                .fit()
                .into(imageView);

        return convertView;
    }
}
