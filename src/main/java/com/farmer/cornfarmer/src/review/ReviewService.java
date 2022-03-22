package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.movie.MovieRepository;
import com.farmer.cornfarmer.src.movie.domain.Movie;
import com.farmer.cornfarmer.src.review.domain.Report;
import com.farmer.cornfarmer.src.review.domain.Review;
import com.farmer.cornfarmer.src.review.model.*;
import com.farmer.cornfarmer.src.user.UserReportRepository;
import com.farmer.cornfarmer.src.user.UserRepository;
import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.UserLikeReview;
import com.farmer.cornfarmer.src.user.domain.UserLikeReviewPK;
import com.farmer.cornfarmer.src.user.domain.UserReport;
import com.farmer.cornfarmer.src.user.enums.ActiveType;
import com.farmer.cornfarmer.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JwtService jwtService;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserLikeReviewRepository userLikeReviewRepository;
    private final ReportRepository reportRepository;
    private final UserReportRepository userReportRepository;

    @Transactional
    public PostReviewRes createReview(long userIdx, PostReviewReq postReviewReq) throws BaseException {
        try {
            Movie movie = movieRepository.findById(postReviewReq.getMovieIdx())
                    .orElseThrow(EntityNotFoundException::new);
            User user = userRepository.findById(userIdx)
                    .orElseThrow(EntityNotFoundException::new);
            Review review = Review.createReview(postReviewReq,movie,user);
            reviewRepository.save(review);
            return new PostReviewRes(review.getReviewIdx());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void modifyReview(long reviewIdx, long userIdx, PutReviewReq putReviewReq) throws BaseException {
        validateReviewExist(reviewIdx);  //해당 review가 물리적으로 존재하는지 확인
        validateUserIdx(reviewIdx, userIdx);  //접근한 유저와 review 작성자가 일치하는지 확인
        try {
            Review review = reviewRepository.findById(reviewIdx)
                    .orElseThrow(EntityNotFoundException::new);
            review.update(putReviewReq);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteReview(long reviewIdx, long userIdx) throws BaseException {
        validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        validateUserIdx(reviewIdx, userIdx);  //접근한 유저와 review 작성자가 일치하는지 확인
        try {
            Review review = reviewRepository.findById(reviewIdx)
                    .orElseThrow(EntityNotFoundException::new);
            review.delete();
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public PutLikeReviewRes likeReview(long reviewIdx, long userIdx) throws BaseException {
        validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        try {
            //좋아요 누른 기록이 있는지 검사 후, 없다면 좋아요 DB 생성, 있다면 좋아요 DB 삭제
            User user = userRepository.findById(userIdx)
                    .orElseThrow(EntityNotFoundException::new);
            Review review = reviewRepository.findById(reviewIdx)
                    .orElseThrow(EntityNotFoundException::new);
            UserLikeReviewPK id = new UserLikeReviewPK(user.getUserIdx(),review.getReviewIdx());
            if (!userLikeReviewRepository.existsById(id)) {
                userLikeReviewRepository.save(new UserLikeReview(user,review));  //좋아요 생성, review 테이블의 like_cnt를 +1
                return new PutLikeReviewRes("해당 리뷰에 공감합니다.");
            } else {
                userLikeReviewRepository.delete(new UserLikeReview(user,review));    //좋아요 삭제, review 테이블의 like_cnt를 -1
                return new PutLikeReviewRes("해당 리뷰에 대한 공감을 취소합니다.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public PostReportRes createReport(long reviewIdx, long userIdx, PostReportReq postReportReq) throws BaseException {
        validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        try {
            Review review = reviewRepository.findById(reviewIdx)
                    .orElseThrow(EntityNotFoundException::new);
            User user = userRepository.findById(userIdx)
                    .orElseThrow(EntityNotFoundException::new);
            Report report = Report.createReport(review,user,postReportReq);
            reportRepository.save(report);
            if(postReportReq.isReportUser()){
                userReportRepository.save(new UserReport(review.getWriter().getUserIdx()));
            }
            if(postReportReq.isBanUser()){
                User writer = userRepository.findById(review.getWriter().getUserIdx())
                                .orElseThrow(EntityNotFoundException::new);
                writer.updateInactive();
            }
            return new PostReportRes(report.getReportIdx());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void validateUserIdx(long reviewIdx, long userIdx) throws BaseException {
        Review review = reviewRepository.findById(reviewIdx)
                .orElseThrow(EntityNotFoundException::new);
        if (userIdx != review.getWriter().getUserIdx())   //리뷰 작성자와 접근한 유저가 일치하는지 확인
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }

    public void validateReviewExist(long reviewIdx) throws BaseException {
        Review review = reviewRepository.findById(reviewIdx)
                .orElseThrow(EntityNotFoundException::new);
        if(review.getActive().equals(ActiveType.INACTIVE)){  //논리적으로 삭제된 리뷰인지 확인
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW);
        }
    }


}
