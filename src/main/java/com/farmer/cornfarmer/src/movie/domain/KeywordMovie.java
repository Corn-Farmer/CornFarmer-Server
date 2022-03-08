package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="keyword_movie",
        uniqueConstraints = {
        @UniqueConstraint(name = "uni_keyword_movie_1", columnNames = {"movie_idx", "keyword_idx"})
}
)
public class KeywordMovie {

    @Id
    @Column(name ="keyword_movie_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordMovieIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_idx", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="keyword_idx", nullable = false)
    private Keyword keyword;
}
