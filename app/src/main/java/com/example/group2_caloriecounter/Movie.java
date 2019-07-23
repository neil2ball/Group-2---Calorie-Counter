package com.example.group2_caloriecounter;

/**
 * Created by ankit on 27/10/17.
 */

public class Movie {

    public int id;
    public String food;
    public String count;
    public String date;

    public Movie() {

    }

    public Movie(int id, String food, String count, String date) {

        this.food = food;
        this.count = count;
        this.date = date;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date; }
}