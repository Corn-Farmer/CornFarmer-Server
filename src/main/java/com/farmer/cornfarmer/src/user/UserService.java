package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.movie.GenreRepository;
import com.farmer.cornfarmer.src.movie.OttRepository;
import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.*;
import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OttRepository ottRepository;
    private final GenreRepository genreRepository;
    private final UserOttRepository userOttRepository;
    private final UserGenreRepository userGenreRepository;

    @Autowired
    public UserService(UserDao userDao,  JwtService jwtService, UserRepository userRepository, GenreRepository genreRepository,OttRepository ottRepository, UserOttRepository userOttRepository, UserGenreRepository userGenreRepository ) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.ottRepository = ottRepository;
        this.genreRepository = genreRepository;
        this.userOttRepository = userOttRepository;
        this.userGenreRepository = userGenreRepository;
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
            return UserServiceUtils.checkExistOauthId(userRepository,oauth_id);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public String checkOauthId(String oauth_id) throws BaseException {
        //db에 oauthid 존재하는지 확인
        try {
            return UserServiceUtils.checkOauthId(userRepository, oauth_id);
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
            return UserServiceUtils.checkUserNickname(userRepository,oauth_id);
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
            GetUserSimpleInfo userMySimpleInfo = UserServiceUtils.toGetUserSimpleInfo(userRepository, userIdx);
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
            return UserServiceUtils.getMyInfo(userRepository, userIdx);
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

    public boolean duplicateNick(String nickname) {
        if (UserServiceUtils.IsExistsUserNickName(userRepository,nickname)) {
            return true;
        } else
            return false;
    }

    public boolean ModifyNickNameCheck(String nickname, int userIdx) {
        if(UserServiceUtils.IsExistsUserNickName(userRepository,nickname))
        {
            User user = UserServiceUtils.findUserByUserIdx(userRepository, userIdx);
            if(user.getNickname().equals(nickname))
            {
                return false;
            }
            return true;
        } else
            return false;
    }
    public String getCurrentUserPhoto(int userIdx){
        String photo = UserServiceUtils.getPhoto(userRepository, userIdx);
        return photo;
    }
    public String getKakaoOauthId(String accessToken) throws BaseException {
        System.out.println("accessToken(getKakaoOauthId) : " + accessToken);
        //access token 으로 oauth_id 가져오기
        int id = 0;
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            /*int responseCode = conn.getResponseCode();
            //System.out.println("responseCode : "+responseCode);
            //응답코드 200번대인지 확인*/
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body : " + result);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parseString(result);

            id = element.getAsJsonObject().get("id").getAsInt();
        } catch (IOException e) { //
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_KAKAO_ERROR);
        }

        return Integer.toString(id);
    }

    public String getNaverOauthId(String accessToken) throws BaseException {
        String header = "Bearer " + accessToken;
        String id = "";
        try {
            String apiurl = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                throw new BaseException(BaseResponseStatus.POST_USERS_NAVER_ERROR);
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parseString(response.toString());

            id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_NAVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_NAVER_ERROR);
        }
        return id;
    }

    public String emptyJwt(String Oauth_id) {
        String jwt = jwtService.createJwt(userDao.getUserIdx(Oauth_id), null, Oauth_id, null);
        return jwt;
    }

    public int createUser(String id, UserSocialType oauth_channel) throws BaseException {
        int result = 0;
        try {
            if(false != checkExistOauthId(id))
            {
                throw new BaseException(BaseResponseStatus.POST_USERS_INVALID_OATUH_ID);
            }
            result = userDao.createUser(id, oauth_channel);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_CREATE_FAILED);
        }
        return result;
    }

    public PostUserRes createUserInfo(PostUserReq postUserReq, String PhotoUrl, String oauth_id) throws BaseException {
        try {
            int userIdx = UserServiceUtils.createUserInfo(ottRepository, genreRepository, userRepository, userOttRepository, userGenreRepository,  postUserReq, PhotoUrl, oauth_id);
            return new PostUserRes(userIdx);
        } catch (Exception Exception) {
            System.out.println(Exception);
            throw new BaseException(BaseResponseStatus.POST_USERS_EXIST_OAUTHID);
        }
    }


    public PostLoginRes modifyMyInfo(int userIdx, PostUserInfoReq postUserInfoReq, String PhotoUrl) throws BaseException {
            try{
                UserServiceUtils.modifyMyInfo(genreRepository, ottRepository, userOttRepository, userGenreRepository, userRepository, userIdx, postUserInfoReq, PhotoUrl);
                GetUserInfo getUserInfo = UserServiceUtils.toGetUserInfo(userRepository, userIdx);

                String jwt = jwtService.createJwt(getUserInfo.getUser_idx(), getUserInfo.getOauth_channel(),getUserInfo.getOauth_id(), getUserInfo.getNickname());
                getMyInfo(userIdx);
                return new PostLoginRes(false, jwt, getUserInfo.getUser_idx());

        } catch (BaseException exception) {
            exception.printStackTrace();
            System.out.println(exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }


    public PostUserRes deleteUser(int userIdx) throws BaseException {
        try{
            if(userIdx == jwtService.getUserIdx()) {
                int result = UserServiceUtils.deleteUser(userRepository, userIdx);
                return new PostUserRes(result);
            } else {
                throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
