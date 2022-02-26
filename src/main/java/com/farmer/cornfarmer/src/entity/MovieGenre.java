package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_genre")
public class MovieGenre {
    @Id
    @GeneratedValue
    private int idx;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_idx", nullable = false)
    private List<Movie> movies = new ArrayList<>();

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_idx", nullable = false)
    private List<Genre> genres = new ArrayList<>();

}
