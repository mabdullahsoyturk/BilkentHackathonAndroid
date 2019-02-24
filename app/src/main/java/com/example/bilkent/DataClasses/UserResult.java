package com.example.bilkent.DataClasses;

public class UserResult {
    private String username;
    private int rank;
    private int score;

    public UserResult(String username, int rank, int score) {
        this.username = username;
        this.rank = rank;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
