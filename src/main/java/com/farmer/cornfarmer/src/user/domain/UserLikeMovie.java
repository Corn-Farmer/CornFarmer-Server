package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_movie")
public class UserLikeMovie {

    @Id
    @Column(name ="user_movie_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeMovieIdx;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;
}
