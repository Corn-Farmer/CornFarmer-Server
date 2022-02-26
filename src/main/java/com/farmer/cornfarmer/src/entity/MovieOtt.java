package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_ott")
public class MovieOtt {

    @Id
    @GeneratedValue
    private int idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_idx", nullable = false)
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ott_idx", nullable = false)
    private List<Ott> otts = new ArrayList<>();

}
