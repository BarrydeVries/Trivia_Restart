package com.example.barry.trivia_restart;

import android.support.annotation.NonNull;

public class Highscore implements Comparable {
    String name;
    int score;

    public Highscore() {
    }

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // compare scores function
    @Override
    public int compareTo(@NonNull Object highscore) {
        int compareScore = ((Highscore) highscore).getScore();
        return compareScore - this.score;

    }
}
