package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(UserLikeMoviePK.class)
@Table(name="user_movie")
public class UserLikeMovie {

    @Id
    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="movie_idx")
    private Movie movie;
}
