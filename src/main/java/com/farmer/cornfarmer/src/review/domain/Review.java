package com.farmer.cornfarmer.src.review.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.movie.domain.Movie;
import com.farmer.cornfarmer.src.review.model.PostReviewReq;
import com.farmer.cornfarmer.src.review.model.PutReviewReq;
import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.UserLikeReview;
import com.farmer.cornfarmer.src.user.enums.ActiveType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewIdx;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(targetEntity = Movie.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_idx")
    private Movie movie;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User writer;

    private float rate;

    private ActiveType active;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<UserLikeReview> reviewLikedByUserList = new ArrayList<>();


    @Builder
    private Review(PostReviewReq postReviewReq, Movie movie, User user) {
        this.contents = postReviewReq.getContent();
        this.movie = movie;
        this.writer = user;
        this.rate = postReviewReq.getRate();
        this.active = ActiveType.ACTIVE;
    }


    public void update(PutReviewReq putReviewReq) {
        this.contents = putReviewReq.getContent();
        ;
        this.rate = putReviewReq.getRate();
    }

    public void delete() {
        this.active = ActiveType.INACTIVE;
    }
}
