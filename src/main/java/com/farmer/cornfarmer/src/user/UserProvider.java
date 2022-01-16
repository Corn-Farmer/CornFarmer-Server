package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.user.domain.GetUserInfo;
import com.farmer.cornfarmer.src.user.domain.PostLoginRes;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public boolean checkOauthId(int oauth_id) throws BaseException {
        try {
            userDao.checkKakaoOauth(oauth_id);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public PostLoginRes kakaoLogIn(int oauth_id)
    {
        //토큰에 들어갈정보
        GetUserInfo getUserInfo = userDao.getKakaoUser(oauth_id);
        String jwt = jwtService.createJwt(getUserInfo.getUser_idx(), getUserInfo.getOauth_channel(), getUserInfo.getOauth_id(), getUserInfo.getNickname());

        return new PostLoginRes(false,jwt, getUserInfo.getUser_idx());
    }

}
