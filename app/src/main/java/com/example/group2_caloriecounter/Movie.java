package com.example.group2_caloriecounter;

/**
 * Created by ankit on 27/10/17.
 */

public class Movie {

    public int title;
    public String rating;
    public String year;

    public Movie() {

    }

    public Movie(int title, String rating, String year) {
        this.title = title;
        this.rating = rating;
        this.year = year;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}