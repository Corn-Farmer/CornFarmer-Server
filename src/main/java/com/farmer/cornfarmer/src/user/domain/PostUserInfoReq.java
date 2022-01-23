package com.farmer.cornfarmer.src.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUserInfoReq {
    String userNickname;
    String photo;
    List<Integer> userOtt;
    List<Integer> genreList;
}
