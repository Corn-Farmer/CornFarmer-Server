package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_genre")
public class UserLikeGenre {

    @Id
    @Column(name ="user_genre_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeGenreIdx;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name="genre_idx")
    private Genre genre;
}
