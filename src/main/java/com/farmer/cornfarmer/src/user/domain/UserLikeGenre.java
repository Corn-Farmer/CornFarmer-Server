package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(UserLikeGenrePK.class)
@Table(name="user_genre")
public class UserLikeGenre {


    @Id
    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="genre_idx")
    private Genre genre;
}
