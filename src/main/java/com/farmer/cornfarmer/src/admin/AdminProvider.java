package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.admin.model.*;

import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<GetReviewRes> getAllReviews() throws BaseException {
        try {
            List<GetReviewRes> result = adminDao.getAllReviews();
            return result;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void validateReviewExist(int reviewIdx) throws BaseException {
        int reviewCount = adminDao.getReviewIdx(reviewIdx);
        if (reviewCount == 0) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW);
        }
    }

    public List<GetOttRes> getOtts() throws BaseException {
        try {
            List<GetOttRes> getOttList = adminDao.getOtts();
            return getOttList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetGenreRes> getGenres() throws BaseException {
        try {
            List<GetGenreRes> getGenreList = adminDao.getGenres();
            return getGenreList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMovieRes> getGenreMovies(int genreIdx) throws BaseException {
        try {
            List<GetMovieRes> getMovieResList = adminDao.getGenreMovies(genreIdx);
            return getMovieResList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsers() throws BaseException {
        try {
            // TODO : 성능 향상 방법 고민
            List<GetUserRes> getUserList = adminDao.getUser();
            for (GetUserRes getUserRes : getUserList) {
                getUserRes.setGenreList(adminDao.getUserGenre(getUserRes.getUserIdx()));
                getUserRes.setOttList(adminDao.getUserOtt(getUserRes.getUserIdx()));
            }
            return getUserList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetKeywordRes> getKeywords() throws BaseException {
        try {
            List<GetKeywordRes> getKeywordList = adminDao.getKeywords();
            for (GetKeywordRes getKeywordRes : getKeywordList) {
                getKeywordRes.setMovieList(adminDao.getKeywordMovies(getKeywordRes.getKeywordIdx()));
            }
            return getKeywordList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void validateMovieExist(int movieIdx) throws BaseException {
        int reviewCount = adminDao.getMovieIdx(movieIdx);
        if (reviewCount == 0) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_MOVIE);
        }
    }

    public void validateKeywordExist(int keywordIdx) throws BaseException {
        int reviewCount = adminDao.getKeywordIdx(keywordIdx);
        if (reviewCount == 0) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_KEYWORD);
        }
    }
}
