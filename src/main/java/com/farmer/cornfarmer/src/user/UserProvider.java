package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.secret.Secret;
import com.farmer.cornfarmer.src.user.domain.UserMyInfo;
import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.utils.AES128;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.domain.GetUserInfo;
import com.farmer.cornfarmer.src.user.domain.PostLoginRes;
import com.farmer.cornfarmer.utils.JwtService;
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
  
    public boolean checkOauthId(String oauth_id) throws BaseException {
       //db에 oauthid 존재하는지 확인
        try {
            return userDao.checkOauth(oauth_id);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void validateUser(int userIdx, int userJwtIdx) throws BaseException{
        if(userIdx != userJwtIdx)
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String oauth_id) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return userDao.checkNickname(oauth_id);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public int getUserIdx(String oauth_id) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return userDao.getUserIdx(oauth_id);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public PostLoginRes kakaoLogIn(String oauth_id) throws BaseException {
        try {
            //db에 존재하는 유저정보 가져와서 토큰만들어주기
            GetUserInfo getUserInfo = userDao.getKakaoUser(oauth_id);
            String jwt = jwtService.createJwt(getUserInfo.getUser_idx(), getUserInfo.getOauth_channel(), getUserInfo.getOauth_id(), getUserInfo.getNickname());

            return new PostLoginRes(false, jwt, getUserInfo.getUser_idx());
        }
        catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public PostLoginRes naverLogIn(String oauth_id) throws BaseException {
        try {
            //db에 존재하는 유저정보 가져와서 토큰만들어주기
            GetUserInfo getUserInfo = userDao.getUser(oauth_id);
            String jwt = jwtService.createJwt(getUserInfo.getUser_idx(), getUserInfo.getOauth_channel(), getUserInfo.getOauth_id(), getUserInfo.getNickname());

            return new PostLoginRes(false, jwt, getUserInfo.getUser_idx());
        }
        catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public UserMyInfo getMyInfo() throws BaseException {
        try {
            UserMyInfo userMyInfo = new UserMyInfo();
            userMyInfo.setNickname(jwtService.getNickname());
            userMyInfo.setPhoto(userDao.getPhoto(jwtService.getUserIdx()));
            return userMyInfo;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
