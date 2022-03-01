package com.farmer.cornfarmer.src.movie.domain;

import com.farmer.cornfarmer.src.user.domain.UserLikeGenre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreIdx;

    @Column(name = "genre_name", nullable = false)
    private Long name;

    @OneToMany(mappedBy = "genre")
    private List<UserLikeGenre> genreLikedByUserList = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> movieGenreList = new ArrayList<>();
}
