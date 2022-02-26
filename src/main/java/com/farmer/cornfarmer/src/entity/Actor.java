package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue
    private int idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_idx", nullable = false)
    private List<Movie> movies = new ArrayList<>();


    @Column(nullable = false)
    private String actor_name;
}
