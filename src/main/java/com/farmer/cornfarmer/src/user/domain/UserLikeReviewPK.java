package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class UserLikeReviewPK implements Serializable {

    private User user;

    private Review review;

}