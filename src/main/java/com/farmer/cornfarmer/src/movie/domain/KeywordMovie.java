package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="keyword_movie")
public class KeywordMovie {

    @Id
    @Column(name ="keyword_movie_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordMovieIdx;

    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="keyword_idx")
    private Keyword keyword;
}
