package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@IdClass(UserLikeReviewPK.class)
@Table(name="user_review")
public class UserLikeReview {

    @Id
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private User user;

    @Id
    @ManyToOne(targetEntity = Review.class, fetch = FetchType.LAZY)
    @JoinColumn(name="review_idx")
    private Review review;


    public UserLikeReview(User user,Review review) {
        this.user = user;
        this.review = review;
    }
}
