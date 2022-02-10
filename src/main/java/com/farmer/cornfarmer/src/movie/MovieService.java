package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MovieDao movieDao;
    //private final MovieProvider movieProvider;
    private final JwtService jwtService;

    @Autowired
    public MovieService(MovieDao movieDao, JwtService jwtService) {
        this.movieDao = movieDao;
        //this.movieProvider = movieProvider;
        this.jwtService = jwtService;
    }
    public boolean isLogin() throws BaseException {
        try{
            int userIdx=jwtService.getUserIdx();
            System.out.println("로그인 되어있는 사용자입니다.");
            return true;
        }
        catch (Exception exception){
            System.out.println("로그인 되어있지 않은 사용자입니다.");
            return false;
        }

    }

    //로그인 안했으면 jwt입력해달라는 오류 반환.
    public void validateUser() throws BaseException {

        try{
            int userIdx=jwtService.getUserIdx();
        }
        catch (Exception exception){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
    }

}
