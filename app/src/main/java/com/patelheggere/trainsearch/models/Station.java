package com.patelheggere.trainsearch.models;

/**
 * Created by Talkative Parents on 1/11/2018.
 */

public class Station{
    private String id;
    private String name;
    private String score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}