package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewProvider {

    private final ReviewDao reviewDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public boolean checkReviewLike(int reviewIdx, int userIdx) throws BaseException {
        try {
            boolean result = reviewDao.checkReviewLike(reviewIdx, userIdx);
            return result;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void validateUserIdx(int reviewIdx, int userIdx) throws BaseException {
        int reviewUserIdx = reviewDao.getUserIdx(reviewIdx);
        if (userIdx != reviewUserIdx)
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }

    public void validateReviewExist(int reviewIdx) throws BaseException {
        int reviewCount = reviewDao.getReviewIdx(reviewIdx);
        if (reviewCount == 0) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW);
        }
    }
}
