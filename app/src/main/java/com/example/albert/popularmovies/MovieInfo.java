package com.example.albert.popularmovies;

/**
 * Created by Albert on 3/16/17.
 */

public class MovieInfo {
    String posterURL;
    String movieName;
    String overview;
    String usrRating;
    String releaseDate;

    public MovieInfo (String mURL, String mName, String mOverview, String mRating, String mDate) {
        this.posterURL = mURL;
        this.movieName = mName;
        this.overview = mOverview;
        this.usrRating = mRating;
        this.releaseDate = mDate;
    }

    public String getMoviePosterURL() {
        return posterURL;
    }
}
