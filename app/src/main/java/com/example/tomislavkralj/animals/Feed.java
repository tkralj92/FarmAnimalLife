package com.example.tomislavkralj.animals;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public class Feed {

    private int id;
    private String feedName;
    private int feedCalories;

    public Feed() {
    }

    public Feed(int id, String feedName, int feedCalories) {
        this.feedName = feedName;
        this.feedCalories = feedCalories;
        this.id = id;
    }
    public Feed( String feedName, int feedCalories) {
        this.feedName = feedName;
        this.feedCalories = feedCalories;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public int getFeedCalories() {
        return feedCalories;
    }

    public void setFeedCalories(int feedCalories) {
        this.feedCalories = feedCalories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
