package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.model.GetUserInfo;
import com.farmer.cornfarmer.src.user.model.PostLoginRes;
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
    public List<GetMyReviewRes> getMyReviews(int userIdx, int userJwtIdx, String sort) throws BaseException {
        validateUser(userIdx, userJwtIdx);   //pathvariable userId와 로그인한 유저 정보(userJwtIdx)가 동일한지 확인
        try {
            List<GetMyReviewRes> result = userDao.getMyReviews(userIdx, sort);
            return result;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public boolean checkExistOauthId(String oauth_id) throws BaseException {
       //db에 oauthid 존재하는지 확인
        try {
            return userDao.checkExistOauthid(oauth_id);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public boolean checkBan(int src_user_idx, int dest_user_idx) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return userDao.checkBan(src_user_idx, dest_user_idx);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public String checkOauthId(String oauth_id) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return userDao.checkOauthid(oauth_id);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void validateUser(int userIdx, int userJwtIdx) throws BaseException {
        if (userIdx != userJwtIdx)   //pathvariable userId와 로그인한 유저 정보(userJwtIdx)가 동일한지 확인
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }

    @Transactional(readOnly = true)
    public String checkUserNickname(String oauth_id) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return userDao.checkUserNickName(oauth_id);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_INVALID_NICKNAME);
        }
    }

    @Transactional(readOnly = true)
    public String getUserNickname(int userIdx) throws BaseException {
        try {
            return userDao.getUserNickName(userIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_INVALID_NICKNAME);
        }
    }

    @Transactional(readOnly = true)
    public int getUserIdx(String oauth_id) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return userDao.getUserIdx(oauth_id);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public GetUserSimpleInfo getUserSimpleInfo(int userIdx) throws BaseException {
        try {
            GetUserSimpleInfo userMySimpleInfo = userDao.getUserSimpleInfo(userIdx);
            userMySimpleInfo.setGenreList(userDao.getGenre(userIdx));
            userMySimpleInfo.setOttList(userDao.getOtt(userIdx));
            return userMySimpleInfo;
        } catch (Exception exception) {
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
        } catch (Exception exception) {
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
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public UserMyInfo getMyInfo(int userIdx) throws BaseException {
        try {
            UserMyInfo userMyInfo = userDao.getUserInfo(userIdx);
            userMyInfo.setGenreList(userDao.getGenreInfo(userIdx));
            userMyInfo.setOttList(userDao.getOttInfo(userIdx));
            return userMyInfo;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetMyMovieLikedRes> getMyMoviesLiked(int userIdx, int userJwtIdx) throws BaseException {
        validateUser(userIdx, userJwtIdx);   //pathvariable userId와 로그인한 유저 정보(userJwtIdx)가 동일한지 확인
        try {
            List<GetMyMovieLikedRes> result = userDao.getMyMoviesLiked(userIdx);
            return result;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public boolean checkActive(String oauth_id) {
        if(userDao.checkActive(oauth_id))
        {
            return true;
        }
        else
            return false;
    }

    public boolean duplicateNick(String nickname) {
        if (userDao.duplicateNick(nickname)) {
            return true;
        } else
            return false;
    }

    public boolean checkDuplicateNick(String nickname, int userIdx) {
        if(userDao.checkDuplicateNick(nickname, userIdx))
        {
            return true;
        } else
            return false;
    }
    public String getCurrentUserPhoto(int userIdx){
        if(userDao.checkMyPhoto(userIdx))
            return userDao.getPhoto(userIdx);
        else
            return "";
    }
}
