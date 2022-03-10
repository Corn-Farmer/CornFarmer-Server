package com.farmer.cornfarmer.src.movie.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성


public class GetMovieDetail {
    public GetMovieDetail(String movieName, int releaseYear, int likeCnt, String synopsis, String director) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.likeCnt = likeCnt;
        this.synopsis = synopsis;
        this.director = director;
    }

    private String movieName;
    private int releaseYear;
    private List<String> moviePhotoList;
    private List<String> movieGenreList;
    private boolean isLiked;
    private int likeCnt;
    private List<Ott> ottList;
    private String synopsis;
    private List<Review> reviewList;
    private String director;
    private List<String> actorList;
}
