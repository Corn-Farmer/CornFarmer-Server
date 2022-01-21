package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.review.model.*;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;
    private final JwtService jwtService;

    @Autowired
    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
        this.jwtService = jwtService;
    }

    @Transactional
    public PostReviewRes createReview(int userIdx, PostReviewReq postReviewReq) throws BaseException {
        try{
            PostReviewRes postReviewRes = reviewDao.createReview(userIdx,postReviewReq);
            return postReviewRes;
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void modifyReview(int reviewIdx, int userIdx, PutReviewReq putReviewReq) throws BaseException {
        reviewProvider.validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        reviewProvider.validateUserIdx(reviewIdx,userIdx);  //접근한 유저와 review 작성자가 일치하는지 확인
        try{
            int result = reviewDao.modifyReview(reviewIdx,putReviewReq);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_REVIEW);
            }
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteReview(int reviewIdx,int userIdx) throws BaseException {
        reviewProvider.validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        reviewProvider.validateUserIdx(reviewIdx,userIdx);  //접근한 유저와 review 작성자가 일치하는지 확인
        try{
            int result = reviewDao.deleteReview(reviewIdx);
            if(result == 0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_REVIEW);
            }
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void likeReview(int reviewIdx, int userIdx) throws BaseException {
        reviewProvider.validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        try{
        //좋아요 누른 기록이 있는지 검사 후, 없다면 좋아요 DB 생성, 있다면 좋아요 DB 삭제
        boolean isReviewLikeExist = reviewProvider.checkReviewLike(reviewIdx,userIdx);
        if(isReviewLikeExist == false){
            createReviewLike(reviewIdx,userIdx);    //좋아요 생성, review 테이블의 like_cnt를 +1
        }else {
            deleteReviewLike(reviewIdx, userIdx);   //좋아요 삭제, review 테이블의 like_cnt를 -1
        }
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void createReviewLike(int reviewIdx, int userIdx) throws BaseException {
            int result = reviewDao.createReviewLike(reviewIdx,userIdx);
            if(result == 0){
                throw new BaseException(BaseResponseStatus.CREATE_FAIL_REVIEWLIKE);
            }
    }

    public void deleteReviewLike(int reviewIdx, int userIdx) throws BaseException {
            int result = reviewDao.deleteReviewLike(reviewIdx,userIdx);
            if(result == 0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_REVIEWLIKE);
            }
    }

    @Transactional
    public PostReportRes createReviewReport(int reviewIdx, int userIdx, PostReportReq postReportReq) throws BaseException {
        reviewProvider.validateReviewExist(reviewIdx);  //해당 review가 존재하는지 확인
        try{
            PostReportRes postReportRes = reviewDao.createReviewReport(reviewIdx,userIdx,postReportReq);
            return postReportRes;
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


}
