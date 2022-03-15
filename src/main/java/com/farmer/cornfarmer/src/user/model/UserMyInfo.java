package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMyInfo {
    private String nickname;
    private String photo;
    private List<OttInfo> ottList;
    private List<GenreInfo> genreList;
    private Integer is_male;
    private String birth;

}


