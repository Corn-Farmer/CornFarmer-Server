package com.farmer.cornfarmer.src.admin.model;

import com.farmer.cornfarmer.src.review.model.Movie;
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
