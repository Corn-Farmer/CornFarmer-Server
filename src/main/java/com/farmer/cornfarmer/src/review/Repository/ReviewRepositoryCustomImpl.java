package com.farmer.cornfarmer.src.review.Repository;

import com.farmer.cornfarmer.src.review.model.GetReviewRes;
import com.farmer.cornfarmer.src.movie.domain.QPhoto;
import com.farmer.cornfarmer.src.user.enums.ActiveType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.cornfarmer.src.movie.domain.QMovie.movie;
import static com.farmer.cornfarmer.src.review.domain.QReview.review;
import static com.farmer.cornfarmer.src.user.domain.QUser.user;
import static com.farmer.cornfarmer.src.user.domain.QUserLikeReview.userLikeReview;


public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
//    @Query("select r.review_idx,m.movie_idx,m.movie_title, u.user_idx,r.contents,r.rate,r.like_cnt,r.created_at,u.ninckname, m.movie_photo " +
//            " from review as r " +
//            "left join (select movie_idx,movie_title,movie_photo from movie left join movie_photo on movie.movie_idx = movie_photo.movie_idx ) as m on r.movie_idx = m.movie_idx " +
//            "left join user u on r.user_idx = u.user_idx where r.active = ?")
//int review_idx, int movie_idx, int user_idx, String contents, float rate, int like_cnt, String created_at, String movie_title, String movie_photo, String user_nickName
    public List<GetReviewRes> findAllReviewList() {

        List<GetReviewRes> result = queryFactory
                .select(Projections.constructor(GetReviewRes.class,
                        review.reviewIdx, movie.movieIdx, user.userIdx, review.contents, review.rate,
                        JPAExpressions.select(userLikeReview.count())
                                .from(userLikeReview)
                                .where(userLikeReview.review.reviewIdx.eq(review.reviewIdx)),
                        review.createdAt, movie.title, user.nickname,
                        JPAExpressions.select(QPhoto.photo1.photo)
                                .from(QPhoto.photo1)
                                .where(QPhoto.photo1.movie.eq(movie))
                                .limit(1))
                )
                .from(review)
                .leftJoin(review.writer, user).on(review.writer.eq(user))
                .leftJoin(review.movie, movie).on(review.movie.eq(movie))
                .where(review.active.eq(ActiveType.ACTIVE))
                .fetch();
        return result;
    }
}
