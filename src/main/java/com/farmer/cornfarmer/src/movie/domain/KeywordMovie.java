package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(KeywordMoviePK.class)
@Table(name="keyword_movie")
public class KeywordMovie {

    @Id
    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name="keyword_idx")
    private Keyword keyword;
}
