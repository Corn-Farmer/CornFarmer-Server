package com.farmer.cornfarmer.src.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue
    private int movie_idx;

    @Column(nullable = false)
    private String movie_title;

    private int release_year;

    @Column(nullable = false)
    private String synopsis;

    private String director;

    @ColumnDefault(value = "0")
    private int like_cnt = 0;
}
