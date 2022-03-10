package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.movie.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.farmer.cornfarmer.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MovieProvider {

    private final MovieDao movieDao;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MovieProvider(MovieDao movieDao) {
        this.movieDao = movieDao;
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
            List<GetGenre> movieGenre = movieDao.getMovieGenre(movieIdx);
            return movieGenre;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetLike getLike(int userIdx, int movieIdx) throws BaseException {
        try {
            GetLike like = movieDao.getLike(userIdx, movieIdx);
            return like;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteFromWish(int userIdx, int movieIdx) {
        movieDao.deleteFromWish(userIdx, movieIdx);
    }

    public void addFromWish(int userIdx, int movieIdx) {
        movieDao.addFromWish(userIdx, movieIdx);
    }

    public List<GetMovieInfo> getMovieIdx_Today() throws BaseException {
        try {
            List<GetMovieInfo> movieidx = movieDao.getMovieIdx_Today();
            return movieidx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetMovieInfo getMovieToday(int movieIdx) throws BaseException {
        try {
            GetMovieInfo movieinfo = movieDao.getMovieToday(movieIdx);
            return movieinfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetGenre> getMoviePhoto(int movieIdx) throws BaseException {
        try {
            List<GetGenre> moviePhoto = movieDao.getMoviePhoto(movieIdx);
            return moviePhoto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetMovieDetail getMovieDetail(int movieIdx) throws BaseException {
        try {
            GetMovieDetail getMovieDetail = movieDao.getMovieDetail(movieIdx);
            return getMovieDetail;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Ott> getOtt(int movieIdx) throws BaseException {
        try {
            List<Ott> ott = movieDao.getOtt(movieIdx);
            return ott;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<String> getActorList(int movieIdx) throws BaseException {
        try {
            List<String> actorList = movieDao.getActorList(movieIdx);
            return actorList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Review> getReview_recent(int movieIdx) throws BaseException {
        try {
            List<Review> reviewList = movieDao.getReview_recent(movieIdx);
            return reviewList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Review> getReview_like(int movieIdx) throws BaseException {
        try {
            List<Review> reviewList = movieDao.getReview_like(movieIdx);
            return reviewList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetLike getreviewLike(int userIdx, int reviewIdx) throws BaseException {
        try {
            GetLike like = movieDao.getreviewLike(userIdx,reviewIdx);
            return like;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public Writer getWriter(int userIdx) throws BaseException {
        try {
            Writer writer = movieDao.getWriter(userIdx);
            return writer;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMovieInfo> getMovieIdx_Search(String keyword, String sort) throws BaseException {
        try {
            List<GetMovieInfo> movieidx = movieDao.getMovieIdx_Search(keyword, sort);
            return movieidx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetLike getLikeCnt(int movieIdx) throws BaseException {
        try {
            GetLike getLike = movieDao.getLikeCnt(movieIdx);
            return getLike;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMovieInfo> getMovieIdxRand() throws BaseException {
        try {
            String today = dateFormatter.format(new Date());
            List<GetMovieInfo> movieidx = movieDao.getMovieIdxRand(today);
            return movieidx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
