package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="movie_ott",
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_movie_ott_1", columnNames = {"movie_idx", "ott_idx"})
        }
)
public class MovieOtt {

    @Id
    @Column(name ="movie_ott_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieOttIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_idx", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ott_idx", nullable = false)
    private Ott ott;
}
