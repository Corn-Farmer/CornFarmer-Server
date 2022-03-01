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
@Table(name="user_ott")
public class UserLikeOtt {

    @Id
    @Column(name ="user_ott_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeOttIdx;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name="ott_idx")
    private Ott ott;
}
