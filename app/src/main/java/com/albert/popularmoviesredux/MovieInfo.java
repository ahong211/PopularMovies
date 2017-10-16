package com.albert.popularmoviesredux;

/**
 * Created by Albert on 10/11/17.
 */

public class MovieInfo {

    String title;
    String description;

    public MovieInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
