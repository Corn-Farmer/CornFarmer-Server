package com.farmer.cornfarmer.src.movie.domain;

import com.farmer.cornfarmer.src.user.domain.UserLikeMovie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Movie {

    //TODO : DB에서 like_cnt 지우고 싶어요..!!(그래서 안했어요)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieIdx;

    @Column(name = "movie_title", nullable = false)
    private String title;

    @Column(nullable = false)
    private String synopsis;

    @Column(name = "release_year", nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String director;

    @OneToMany(mappedBy = "movie")
    private List<UserLikeMovie> movieLikedByUserList = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> movieGenreList = new ArrayList<>();
}
