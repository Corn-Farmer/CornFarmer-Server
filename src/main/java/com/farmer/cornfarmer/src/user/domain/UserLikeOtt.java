package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Ott;
import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Entity
@IdClass(UserLikeOttPK.class)
@Table(name="user_ott")
public class UserLikeOtt {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ott_idx")
    private Ott ott;
}
