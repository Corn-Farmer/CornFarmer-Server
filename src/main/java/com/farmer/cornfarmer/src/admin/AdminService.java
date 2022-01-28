package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.admin.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.farmer.cornfarmer.config.BaseResponseStatus.*;

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
        adminProvider.validateReviewExist(reviewIdx); //이미 삭제된 리뷰인지 확인
        try{
            int result = adminDao.deleteReview(reviewIdx);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_REVIEW);
            }
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);

        }
    }

    public PostGenreRes createGenre(PostGenreReq postGenreReq) throws BaseException {

        try {
            int genreIdx = adminDao.createGenre(postGenreReq);
            return new PostGenreRes(genreIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostOttRes createOtt(String ottName, String ottFileURL) throws BaseException {
        try {
            int ottIdx = adminDao.createOtt(ottName, ottFileURL);
            return new PostOttRes(ottIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostMovieRes createMovie(List<String> moviePhotoURLs, PostMovieReq postMovieReq) throws BaseException {
        // TODO : Transactional 고려하기
        try {
            int releaseYear = Integer.parseInt(postMovieReq.getReleaseYear());
            // 영화 생성
            int movieIdx = adminDao.createMovie(postMovieReq.getMovieTitle(), releaseYear,
                    postMovieReq.getSynopsis(), postMovieReq.getDirector());
            // 영화-장르 연관성 생성
            for(String str_genreIdx : postMovieReq.getGenreList()) {
                int genreIdx = Integer.parseInt(str_genreIdx);
                adminDao.createMovieGenre(movieIdx, genreIdx);
            }
            // 영화-OTT 연관성 생성
            for(String str_ottIdx : postMovieReq.getOttList()) {
                int ottIdx = Integer.parseInt(str_ottIdx);
                adminDao.createMovieOtt(movieIdx, ottIdx);
            }
            // 영화-사진 연관성 생성
            for(String photoURL : moviePhotoURLs)
                adminDao.createMoviePhoto(movieIdx, photoURL);
            return new PostMovieRes(movieIdx);
        }catch (NumberFormatException exception) {
            throw new BaseException(REQUEST_ERROR);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostKeywordMovieRes createKeywordMovies(int keywordIdx, List<Integer> MovieList) throws BaseException {
        // TODO : Transactional 고려하기
        try {
            // 영화-키워드 연관성 생성
            for(int movieIdx : MovieList) {
                adminDao.createMovieKeyword(keywordIdx, movieIdx);
            }
            return new PostKeywordMovieRes(keywordIdx);
        } catch (DuplicateKeyException duplicateKeyException){
            throw new BaseException(DUPLICATE_KEY_ERROR);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteMovie(int movieIdx) throws BaseException {
        adminProvider.validateMovieExist(movieIdx); //이미 삭제된 영화인지 확인
        try{
            int result = adminDao.deleteMovie(movieIdx);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_MOVIE);
            }
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteKeyword(int keywordIdx) throws BaseException {
        adminProvider.validateKeywordExist(keywordIdx); //이미 삭제된 키워드인지 확인
        try{
            int result = adminDao.deleteKeyword(keywordIdx);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_KEYWORD);
            }
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
