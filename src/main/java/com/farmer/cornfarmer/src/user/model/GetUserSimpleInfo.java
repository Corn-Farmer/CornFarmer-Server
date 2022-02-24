package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserSimpleInfo {
    private String nickname;
    private String photo;
    private List<Integer> ottList;
    private List<Integer> genreList;
    private Integer is_male;
    private String birth;
}
