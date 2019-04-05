package com.rstudio.cmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

public class Movie implements Parcelable {
    private String imageLink;
    private String videoLink;
    private String movieDescription;
    private float rating;
    private int year;
    private String movieName;


    public Movie() {}

    public Movie(String imageLink, String movieName, String movieDescription, String videoLink) {
        this.imageLink = imageLink;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.videoLink = videoLink;
    }


    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    protected Movie(Parcel in) {
        imageLink = in.readString();
        videoLink = in.readString();
        movieDescription = in.readString();
        rating = in.readFloat();
        year = in.readInt();
        movieName = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageLink);
        dest.writeString(videoLink);
        dest.writeString(movieDescription);
        dest.writeFloat(rating);
        dest.writeInt(year);
        dest.writeString(movieName);
    }
}
