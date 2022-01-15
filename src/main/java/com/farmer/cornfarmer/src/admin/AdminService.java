package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.admin.model.PostGenreReq;
import com.farmer.cornfarmer.src.admin.model.PostGenreRes;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.farmer.cornfarmer.config.BaseResponseStatus.DATABASE_ERROR;

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

    public PostGenreRes createGenre(PostGenreReq postGenreReq) throws BaseException {

        try {
            int genreIdx = adminDao.createGenre(postGenreReq);
            return new PostGenreRes(genreIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
