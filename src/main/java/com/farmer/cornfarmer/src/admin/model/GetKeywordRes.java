package com.farmer.cornfarmer.src.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetKeywordRes {
    private int keywordIdx;
    private String keywordName;
    private List<Movie> movieList;
}
