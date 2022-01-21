package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.secret.Secret;
import com.farmer.cornfarmer.src.user.model.GetMyReviewRes;
import com.farmer.cornfarmer.utils.AES128;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public List<GetMyReviewRes> getMyReviews(int userIdx,int userJwtIdx) throws BaseException {
        validateUser(userIdx,userJwtIdx);
        try{
            List<GetMyReviewRes> result = userDao.getMyReviews(userIdx);
            return result;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void validateUser(int userIdx, int userJwtIdx) throws BaseException{
        if(userIdx != userJwtIdx)
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }

}
