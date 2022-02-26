package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private int review_idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx", nullable = false)
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_idx", nullable = false)
    private List<Movie> movies = new ArrayList<>();

    private String contents;

    private Float rate;

    private Boolean active;

    private int like_cnt;

}
