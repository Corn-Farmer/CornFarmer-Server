package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMyReviewRes {
    private String nickname;
    private List<ReviewInfo> reviewList;

    public GetMyReviewRes(String nickname)
    {
        this.nickname = nickname;
    }
}
