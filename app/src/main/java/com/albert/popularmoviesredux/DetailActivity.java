package com.albert.popularmoviesredux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    MovieInfo holdMovieDetail;
    TextView detailOriginalTitle;
    TextView detailOverview;
    TextView detailReleaseDate;
    TextView detailVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailOriginalTitle = findViewById(R.id.tv_detail_original_title);
        detailOverview = findViewById(R.id.tv_detail_overview);
        detailReleaseDate = findViewById(R.id.tv_detail_release_date);
        detailVoteAverage = findViewById(R.id.tv_detail_vote_average);

        Intent getMovieDetailsIntent = getIntent();

        if (getMovieDetailsIntent.hasExtra("movieData")) {
            holdMovieDetail = getMovieDetailsIntent.getParcelableExtra("movieData");
            detailOriginalTitle.setText(holdMovieDetail.getOriginalTitle());
            detailOverview.setText(holdMovieDetail.getOverview());
            detailReleaseDate.setText(holdMovieDetail.getReleaseDate());
            detailVoteAverage.setText(holdMovieDetail.getVoteAverage());

        }
    }
}
