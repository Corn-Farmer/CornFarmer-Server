package com.farmer.cornfarmer.src.movie.domain;

import com.farmer.cornfarmer.src.user.domain.User;
import com.sun.tools.javac.jvm.Gen;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="movie_genre",
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_movie_genre_1", columnNames = {"movie_idx", "genre_idx"})
        }
)
public class MovieGenre {

    @Id
    @Column(name ="movie_genre_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieGenreIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_idx", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_idx", nullable = false)
    private Genre genre;
}
