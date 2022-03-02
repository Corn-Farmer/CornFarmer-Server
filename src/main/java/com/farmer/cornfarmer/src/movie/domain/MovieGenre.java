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
@Table(name="movie_genre")
public class MovieGenre {

    @Id
    @Column(name ="movie_genre_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieGenreIdx;

    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="genre_idx")
    private Genre genre;
}
