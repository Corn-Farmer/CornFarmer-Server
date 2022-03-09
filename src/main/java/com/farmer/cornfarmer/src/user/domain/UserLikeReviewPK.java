package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.review.domain.Review;

import javax.persistence.*;
import java.io.Serializable;

public class UserLikeReviewPK implements Serializable {

    private User user;

    private Review review;
}