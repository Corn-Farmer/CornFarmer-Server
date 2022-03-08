package com.farmer.cornfarmer.src.movie.domain;

import com.farmer.cornfarmer.src.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(MovieGenrePK.class)
@Table(name="movie_genre")
public class MovieGenre {

    @Id
    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name="genre_idx")
    private Genre genre;
}
