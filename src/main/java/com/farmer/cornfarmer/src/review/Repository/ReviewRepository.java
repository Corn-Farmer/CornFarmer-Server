package com.farmer.cornfarmer.src.review.Repository;

import com.farmer.cornfarmer.src.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {


}
