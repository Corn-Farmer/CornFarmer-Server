package com.farmer.cornfarmer.src.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_genre")
public class UserGenre {
    @Id
    @GeneratedValue
    private int idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx",nullable = false)
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_idx",nullable = false)
    private List<Genre> genres = new ArrayList<>();

    @CreationTimestamp
    private Timestamp created_at;
}
