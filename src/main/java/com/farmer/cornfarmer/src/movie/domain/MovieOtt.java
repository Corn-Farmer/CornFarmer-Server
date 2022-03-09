package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(MovieOttPK.class)
@Table(name="movie_ott")
public class MovieOtt {

    @Id
    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name="ott_idx")
    private Ott ott;
}
