package com.example.albert.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Albert on 3/16/17.
 */

public class MovieInfo implements Parcelable{
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

    protected MovieInfo(Parcel in) {
        posterURL = in.readString();
        movieName = in.readString();
        overview = in.readString();
        usrRating = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getMoviePosterURL() {
        return posterURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterURL);
        dest.writeString(movieName);
        dest.writeString(overview);
        dest.writeString(usrRating);
        dest.writeString(releaseDate);
    }
}
