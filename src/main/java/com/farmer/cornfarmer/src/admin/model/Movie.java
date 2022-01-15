package com.farmer.cornfarmer.src.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Movie {
    private int movieIdx;
    private String movieTitle;
    private String moviePhoto;

    Movie(int movieIdx){
        this.movieIdx = movieIdx;
    }

    public Movie(int movie_idx, String movie_title) {
        this.movieIdx = movie_idx;
        this.movieTitle = movie_title;
    }
}


