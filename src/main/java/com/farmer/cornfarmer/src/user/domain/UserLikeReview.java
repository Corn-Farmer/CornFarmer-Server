package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_review")
public class UserLikeReview {

    @Id
    @Column(name ="user_review_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeReviewIdx;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name="review_idx")
    private Review review;
}
