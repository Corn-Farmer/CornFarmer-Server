package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "keyword_movie")
public class KeywordMovie {
    @Id
    @GeneratedValue
    private int idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "keyword_idx", nullable = false)
    private List<Keyword> keywords = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_idx", nullable = false)
    private List<Movie> movies = new ArrayList<>();

}
