package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(UserLikeReviewPK.class)
@Table(name="user_review")
public class UserLikeReview {

    @Id
    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="review_idx")
    private Review review;
}
