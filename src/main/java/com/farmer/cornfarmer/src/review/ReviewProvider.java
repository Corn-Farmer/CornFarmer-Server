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
    public int getUserIdx(int reviewIdx) throws BaseException {
        try{
            int reviewUserIdx = reviewDao.getUserIdx(reviewIdx);
            return reviewUserIdx;
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public boolean checkReviewLike(int reviewIdx, int userIdx) throws BaseException {
        try{
            boolean result = reviewDao.checkReviewLike(reviewIdx,userIdx);
            return result;
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public boolean checkReviewExist(int reviewIdx) throws BaseException {
        try{
            boolean result = reviewDao.checkReviewExist(reviewIdx);
            return result;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
