package com.farmer.cornfarmer.src.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieRes {
    private int movieIdx;
    private String movieTitle;
    private String moviePhoto;
    private int movieLikeCnt;
}
