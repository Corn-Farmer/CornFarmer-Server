package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.admin.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.farmer.cornfarmer.config.BaseResponseStatus.DATABASE_ERROR;
import static com.farmer.cornfarmer.config.BaseResponseStatus.REQUEST_ERROR;

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
}
