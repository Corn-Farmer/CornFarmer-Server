package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="user_movie",
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_user_movie_1", columnNames = {"user_idx", "movie_idx"})
        }
)
public class UserLikeMovie {

    @Id
    @Column(name ="user_movie_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeMovieIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_idx", nullable = false)
    private Movie movie;
}
