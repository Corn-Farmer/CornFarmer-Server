package com.farmer.cornfarmer.src.review.Repository;

import com.farmer.cornfarmer.src.review.model.GetReviewRes;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<GetReviewRes> findAllReviewList();
}
