package com.farmer.cornfarmer.src.movie.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성


/**
 * Res.java: From Server To Client
 * 하나 또는 복수개의 회원정보 조회 요청(Get Request)의 결과(Respone)를 보여주는 데이터의 형태
 *
 * GetUserRes는 클라이언트한테 response줄 때 DTO고
 * User 클래스는 스프링에서 사용하는 Objec이다.
 */
public class GetMovieInfo {
    public GetMovieInfo(int movieIdx, String movieName, String moviePhoto){
        this.movieIdx=movieIdx;
        this.movieName=movieName;
        this.moviePhoto=moviePhoto;
    }
    private int movieIdx;
    private String movieName;
    private String moviePhoto;
    private List<String> movieGenreList;
//    private boolean isLiked;
}
