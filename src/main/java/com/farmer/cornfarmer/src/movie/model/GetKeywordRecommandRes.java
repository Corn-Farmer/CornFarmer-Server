package com.farmer.cornfarmer.src.movie.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성


public class GetKeywordRecommandRes {
    public GetKeywordRecommandRes(String keyword){
        this.keyword=keyword;
    }
    private String keyword;
    private List<GetMovieInfo> movieList;
}
