package com.farmer.cornfarmer.src.user.model;

import com.farmer.cornfarmer.src.admin.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMyReviewRes {
    private int reviewIdx;
    private Movie movie;
    private String contents;
    private float rate;
    private String createdAt;
    private int likeCnt;

    public GetMyReviewRes(int reviewIdx, int movieIdx, String movieTitle, String moviePhoto, String contents, float rate, String createdAt, int likeCnt) {
        this.reviewIdx = reviewIdx;
        this.movie = new Movie(movieIdx,movieTitle,moviePhoto);
        this.contents = contents;
        this.rate = rate;
        this.createdAt = createdAt;
        this.likeCnt = likeCnt;
    }
}
