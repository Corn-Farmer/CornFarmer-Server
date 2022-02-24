package com.farmer.cornfarmer.src.user.model;

import com.farmer.cornfarmer.src.admin.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMyReviewRes {
    private String nickname;
    private int reviewIdx;
    private Movie movie;
    private String content;
    private float rate;
    private String createdAt;
    private int likeCnt;

    public GetMyReviewRes(String nickname, int reviewIdx, int movieIdx, String movieTitle, String moviePhoto, String content, float rate, String createdAt, int likeCnt) {
        this.nickname = nickname;
        this.reviewIdx = reviewIdx;
        this.movie = new Movie(movieIdx, movieTitle, moviePhoto);
        this.content = content;
        this.rate = rate;
        this.createdAt = createdAt;
        this.likeCnt = likeCnt;
    }
}
