package com.farmer.cornfarmer.src.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewRes {
    private int reviewIdx;
    private Movie movie;
    private String userNickName;
    private int userIdx;
    private String content;
    private float rate;
    private int likeCnt;
    private String createdAt;


    public GetReviewRes(int review_idx, int movie_idx, int user_idx, String contents, float rate, int like_cnt, String created_at) {
        this.reviewIdx = review_idx;
        this.movie = new Movie(movie_idx);
        this.userIdx = user_idx;
        this.content = contents;
        this.rate = rate;
        this.likeCnt = like_cnt;
        this.createdAt = created_at;
    }
}
