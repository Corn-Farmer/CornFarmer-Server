package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorIdx;

    @ManyToOne
    @JoinColumn(name = "movie_idx")
    private Movie movie;

    @Column(name = "actor_name", nullable = false)
    private String name;
}
