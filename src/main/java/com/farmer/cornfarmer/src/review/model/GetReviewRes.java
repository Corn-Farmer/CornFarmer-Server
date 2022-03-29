package com.farmer.cornfarmer.src.review.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetReviewRes {
    private long reviewIdx;
    private Movie movie;
    private String userNickName;
    private long userIdx;
    private String content;
    private float rate;
    private long likeCnt;
    private LocalDateTime createdAt;


    public GetReviewRes(long review_idx, long movie_idx, long user_idx, String contents, float rate, long like_cnt, LocalDateTime created_at, String movie_title, String user_nickName, String movie_photo) {
        this.reviewIdx = review_idx;
        this.movie = new Movie(movie_idx, movie_title, movie_photo);
        this.userIdx = user_idx;
        this.content = contents;
        this.rate = rate;
        this.likeCnt = like_cnt;
        this.createdAt = created_at;
        this.userNickName = user_nickName;
    }
}
