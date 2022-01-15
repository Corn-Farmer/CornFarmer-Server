package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.movie.model.GetGenre;
import com.farmer.cornfarmer.src.movie.model.GetKeywordRecommandRes;
import com.farmer.cornfarmer.src.movie.model.GetKeywordRes;
import com.farmer.cornfarmer.src.movie.model.GetMovieInfo;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.farmer.cornfarmer.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MovieProvider {

    private final MovieDao movieDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MovieProvider(MovieDao movieDao, JwtService jwtService) {
        this.movieDao = movieDao;
        this.jwtService = jwtService;
    }

    // User들의 정보를 조회
    public List<GetKeywordRes> getKeywords() throws BaseException {
        try {
            List<GetKeywordRes> GetKeywordRes = movieDao.getKeywords();
            return GetKeywordRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMovieInfo> getmovies(int keywordIdx) throws BaseException {
        try {
            List<GetMovieInfo> movieinfo = movieDao.getMovieInfo(keywordIdx);
            return movieinfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 keywordIdx 갖는 Keyword의 정보 조회
    public GetKeywordRecommandRes getKeyword(int keywordIdx) throws BaseException {
        try {
            GetKeywordRecommandRes getKeywordRecommandRes = movieDao.getKeyword(keywordIdx);
            return getKeywordRecommandRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetGenre> getMovieGenre(int movieIdx) throws BaseException {
        try {
            List <GetGenre> movieGenre = movieDao.getMovieGenre(movieIdx);
            return movieGenre;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
