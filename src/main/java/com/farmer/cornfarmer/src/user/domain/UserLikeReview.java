package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="user_review",
        uniqueConstraints = {
                @UniqueConstraint(name = "uni_user_review_1", columnNames = {"user_idx", "review_idx"})
        }
)
public class UserLikeReview {

    @Id
    @Column(name ="user_review_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLikeReviewIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_idx", nullable = false)
    private Review review;
}
