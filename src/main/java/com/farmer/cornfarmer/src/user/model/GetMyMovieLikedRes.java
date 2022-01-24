package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMyMovieLikedRes {
    private int movieIdx;
    private String movieTitle;
    private String moviePhoto;
    private String movieGenre;
    private int movieLikeCnt;
}
