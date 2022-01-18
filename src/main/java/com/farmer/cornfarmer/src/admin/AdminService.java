package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdminDao adminDao;
    private final AdminProvider adminProvider;
    private final JwtService jwtService;

    @Autowired
    public AdminService(AdminDao adminDao, AdminProvider adminProvider, JwtService jwtService) {
        this.adminDao = adminDao;
        this.adminProvider = adminProvider;
        this.jwtService = jwtService;
    }

    @Transactional
    public void deleteReview(int reviewIdx) throws BaseException {
        try {
            validateReviewExist(reviewIdx); //이미 삭제된 리뷰인지 확인
        }catch (BaseException exception) {
            exception.printStackTrace();
            throw new BaseException(exception.getStatus());
        }
        try{
            int result = adminDao.deleteReview(reviewIdx);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_REVIEW);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);

        }
    }

    public void validateReviewExist(int reviewIdx) throws BaseException {
            int reviewCount = adminDao.getReviewIdx(reviewIdx);
            if(reviewCount == 0){
                throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW);
            }
    }

}
