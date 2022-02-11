package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserMyInfo {
    private String nickname;
    private String photo;
    private List<OttInfo> ottList;
    private List<GenreInfo> genreList;
    private Integer is_male;
    private String birth;
}


