package com.rstudio.cmovies;

public class Movie {
    private String imageUrl;
    private String movieName;

    public Movie() {

    }

    public Movie(String imageUrl, String movieName) {
        this.imageUrl = imageUrl;
        this.movieName = movieName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
