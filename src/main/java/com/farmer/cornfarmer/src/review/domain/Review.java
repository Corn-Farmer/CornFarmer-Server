package com.farmer.cornfarmer.src.review.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.movie.domain.Movie;
import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.UserLikeReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewIdx;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_idx")
    private Movie movie;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User writer;

    private float rate;

    private Boolean active;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<UserLikeReview> reviewLikedByUserList = new ArrayList<>();

}
