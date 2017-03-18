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
    ArrayList<String> poster = new ArrayList<String>();

    public MovieAdapter(Activity context, ArrayList<String> poster) {
        super(context, 0, poster);

        this.mContext = context;
        this.poster = poster;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_posters, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_poster_textview);

        String temp = poster.get(position);

        Picasso.with(mContext)
                .load(temp)
                .fit()
                .into(imageView);

        return convertView;
    }
}
