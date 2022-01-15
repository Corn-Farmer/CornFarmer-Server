package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.admin.model.GetGenreRes;
import com.farmer.cornfarmer.src.admin.model.GetOttRes;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.farmer.cornfarmer.config.BaseResponseStatus.DATABASE_ERROR;

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

    public List<GetOttRes> getOtts() throws BaseException {
        try {
            List<GetOttRes> getOttResList = adminDao.getOtts();
            return getOttResList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
