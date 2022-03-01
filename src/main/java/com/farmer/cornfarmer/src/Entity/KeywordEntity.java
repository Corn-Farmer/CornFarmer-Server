package com.farmer.cornfarmer.src.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "keyword")
public class KeywordEntity {
    @Id
    @Column(name = "keyword_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyword_idx;

    @Column
    private String keyword;

    @ManyToMany //양방향
    @JoinTable(name="keyword_movie",
            joinColumns = @JoinColumn(name = "keyword_idx"),
            inverseJoinColumns = @JoinColumn(name="movie_idx"))
    private List<MovieEntity> movies = new ArrayList<MovieEntity>();


}
