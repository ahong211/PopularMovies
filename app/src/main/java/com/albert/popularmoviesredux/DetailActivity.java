package com.albert.popularmoviesredux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    MovieInfo holdMovieDetail;
    TextView detailOriginalTitle;
    ImageView detailPoster;
    TextView detailOverview;
    TextView detailReleaseDate;
    TextView detailVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailOriginalTitle = findViewById(R.id.tv_detail_original_title);
        detailPoster = findViewById(R.id.iv_detail_movie_poster);
        detailOverview = findViewById(R.id.tv_detail_overview);
        detailReleaseDate = findViewById(R.id.tv_detail_release_date);
        detailVoteAverage = findViewById(R.id.tv_detail_vote_average);

        Intent getMovieDetailsIntent = getIntent();

        if (getMovieDetailsIntent.hasExtra("movieData")) {
            holdMovieDetail = getMovieDetailsIntent.getParcelableExtra("movieData");

            detailOriginalTitle.setText(holdMovieDetail.getOriginalTitle());
            String detailPosterString = getResources().getString(R.string.img_url, holdMovieDetail.getPosterPath());

            Glide.with(this)
                    .load(detailPosterString)
                    .into(detailPoster);

            detailOverview.setText(holdMovieDetail.getOverview());
            detailReleaseDate.setText(holdMovieDetail.getReleaseDate());
            detailVoteAverage.setText(holdMovieDetail.getVoteAverage());

        }
    }
}
