package com.farmer.cornfarmer.src.review.repository;

import com.farmer.cornfarmer.src.user.domain.UserLikeReview;
import com.farmer.cornfarmer.src.user.domain.UserLikeReviewPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeReviewRepository extends JpaRepository<UserLikeReview, UserLikeReviewPK> {
}
