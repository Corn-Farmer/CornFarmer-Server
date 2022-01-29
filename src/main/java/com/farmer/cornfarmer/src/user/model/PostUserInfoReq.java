package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUserInfoReq {
    String nickname;
    String photo;
    List<Integer> userOtt;
    List<Integer> genreList;
}
