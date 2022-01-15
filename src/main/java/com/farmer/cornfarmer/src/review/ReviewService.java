package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.review.model.PostReviewReq;
import com.farmer.cornfarmer.src.review.model.PostReviewRes;
import com.farmer.cornfarmer.src.review.model.PutReviewReq;
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
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void modifyReview(int reviewIdx, PutReviewReq putReviewReq) throws BaseException {
        try{
            int result = reviewDao.modifyReview(reviewIdx,putReviewReq);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_REVIEW);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteReview(int reviewIdx) throws BaseException {
        try{
            int result = reviewDao.deleteReview(reviewIdx);
            if(result == 0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_REVIEW);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void createReviewLike(int reviewIdx, int userIdx) throws BaseException {
        try{
            int result = reviewDao.createReviewLike(reviewIdx,userIdx);
            if(result == 0){
                throw new BaseException(BaseResponseStatus.CREATE_FAIL_REVIEWLIKE);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteReviewLike(int reviewIdx, int userIdx) throws BaseException {
        try{
            int result = reviewDao.deleteReviewLike(reviewIdx,userIdx);
            if(result == 0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_REVIEWLIKE);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
