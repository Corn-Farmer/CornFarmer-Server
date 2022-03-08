package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Ott;
import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="user_ott",
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_user_ott_1", columnNames = {"user_idx", "ott_idx"})
        }
)
public class UserLikeOtt {

    @Id
    @Column(name ="user_ott_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeOttIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ott_idx", nullable = false)
    private Ott ott;
}
