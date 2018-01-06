package com.albert.popularmoviesredux;

/**
 * Created by Albert on 10/11/17.
 */

public class MovieInfo {

    private String originalTitle;
    private String overview;
    private String posterPath;
    private String voteAverage;
    private String releaseDate;

    public MovieInfo(String originalTitle, String overview, String posterPath, String voteAverage, String releaseDate) {
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
