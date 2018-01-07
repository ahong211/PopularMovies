package com.albert.popularmoviesredux.utils;

import com.albert.popularmoviesredux.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Albert on 1/6/18.
 */

public class MovieDBJsonUtils {
    public static MovieInfo[] getMovieDBStringsFromJson(String movieInfoJsonStr) throws JSONException {

        final String MDB_RESULTS = "results";
        final String MDB_ORIGINAL_TITLE = "original_title";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_OVERVIEW = "overview";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_RELEASE_DATE = "release_date";

        MovieInfo[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieInfoJsonStr);

        JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

        parsedMovieData = new MovieInfo[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            MovieInfo movieInfo;
            String originalTitle;
            String posterPath;
            String overview;
            String voteAverage;
            String releaseDate;

            JSONObject movieInfoItem = movieArray.getJSONObject(i);
            originalTitle = movieInfoItem.getString(MDB_ORIGINAL_TITLE);
            posterPath = movieInfoItem.getString(MDB_POSTER_PATH);
            overview = movieInfoItem.getString(MDB_OVERVIEW);
            voteAverage = movieInfoItem.getString(MDB_VOTE_AVERAGE);
            releaseDate = movieInfoItem.getString(MDB_RELEASE_DATE);

            movieInfo = new MovieInfo(originalTitle, overview, posterPath, voteAverage, releaseDate);

            parsedMovieData[i] = movieInfo;
        }

        return parsedMovieData;
    }
}
