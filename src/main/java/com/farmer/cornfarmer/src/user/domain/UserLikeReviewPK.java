package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Movie;
import com.farmer.cornfarmer.src.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeReviewPK implements Serializable {

    private long user;

    private long review;

}