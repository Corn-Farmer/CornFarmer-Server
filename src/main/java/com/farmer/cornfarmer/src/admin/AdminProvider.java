package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.admin.model.GetReviewRes;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProvider {

    private final AdminDao adminDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AdminProvider(AdminDao adminDao, JwtService jwtService) {
        this.adminDao = adminDao;
        this.jwtService = jwtService;
    }

    public List<GetReviewRes> getAllReviews() throws BaseException {
        try{
            List<GetReviewRes> result = adminDao.getAllReviews();
            return result;
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
