package com.farmer.cornfarmer.src.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_movie")
public class UserMovie {
    @Id
    @GeneratedValue
    private int idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx",nullable = false)
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_idx",nullable = false)
    private List<Movie> movies = new ArrayList<>();

    @CreationTimestamp
    private Timestamp created_at;
}
