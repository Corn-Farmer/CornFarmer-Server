package com.farmer.cornfarmer.src.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String userNickname;
    private String photo;
    private List<GetOttRes> ottList;
    private List<GetGenreRes> genreList;
}
