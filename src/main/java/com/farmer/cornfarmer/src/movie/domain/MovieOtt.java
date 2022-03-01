package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="movie_ott")
public class MovieOtt {

    @Id
    @Column(name ="movie_ott_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieOttIdx;

    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="ott_idx")
    private Ott ott;
}
