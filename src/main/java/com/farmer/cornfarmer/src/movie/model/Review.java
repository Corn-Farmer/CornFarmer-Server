package com.farmer.cornfarmer.src.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성

public class Review {
    public Review(int reviewIdx, int userIdx, String contents, float rate, int likeCnt, String createAt) {
        this.reviewIdx = reviewIdx;
        this.userIdx = userIdx;
        this.contents = contents;
        this.rate = rate;
        this.likeCnt = likeCnt;
        this.createAt = createAt;
    }

    private int reviewIdx;
    private int userIdx;
    private Writer writer;
    private String contents;
    private float rate;
    private int likeCnt;
    private String createAt;


}
