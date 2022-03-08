package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="user_genre",
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_user_genre_1", columnNames = {"user_idx", "genre_idx"})
        }
)
public class UserLikeGenre {

    @Id
    @Column(name ="user_genre_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeGenreIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_idx", nullable = false)
    private Genre genre;
}
