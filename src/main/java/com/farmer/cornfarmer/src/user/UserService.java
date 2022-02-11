package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public String getKakaoOauthId(String accessToken) throws BaseException {
        System.out.println("accessToken(getKakaoOauthId) : " + accessToken);
        //access token 으로 oauth_id 가져오기
        int id = 0;
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer "+accessToken);

            /*int responseCode = conn.getResponseCode();
            //System.out.println("responseCode : "+responseCode);
            //응답코드 200번대인지 확인*/
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }

            System.out.println("response body : "+result);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parseString(result);

            id = element.getAsJsonObject().get("id").getAsInt();
        }  catch (IOException e) { //
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_KAKAO_ERROR);
        }

        return Integer.toString(id);
    }

    public String getNaverOauthId(String accessToken) throws BaseException{
        String header = "Bearer "+accessToken;
        String id = "";
        try {
            String apiurl = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
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

            id = element.getAsJsonObject().get("id").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_NAVER_ERROR);
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.POST_USERS_NAVER_ERROR);
        }
        return id;
    }

    public String emptyJwt(String Oauth_id){
        String jwt = jwtService.createJwt(userDao.getUserIdx(Oauth_id), null, Oauth_id, null );
        return jwt;
    }

    public int createUser(String id, String oauth_channel)throws BaseException{
        int result = 0;
        try {
            if(false != userProvider.checkExistOauthId(id))
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

    public PostUserRes createUserInfo(PostUserReq postUserReq, String PhotoUrl, String oauth_id) throws BaseException
    {
        try{
            int userIdx =  userDao.createUserInfo(postUserReq, PhotoUrl, oauth_id);
            return new PostUserRes(userIdx);
        }
        catch (Exception Exception)
        {
            System.out.println(Exception);
            throw new BaseException(BaseResponseStatus.POST_USERS_EXIST_OAUTHID);
        }
    }

    public PostLoginRes modifyMyInfo(int userIdx, PostUserInfoReq postUserInfoReq, String PhotoUrl) throws BaseException {
            try{
                userDao.modifyMyInfo(userIdx, postUserInfoReq, PhotoUrl);
                GetUserInfo getUserInfo = userDao.getUser(userDao.getUserOauth_id(userIdx));

                String jwt = jwtService.createJwt(getUserInfo.getUser_idx(), getUserInfo.getOauth_channel(),getUserInfo.getOauth_id(), getUserInfo.getNickname());
                userProvider.getMyInfo(userIdx);
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

                int result = userDao.deleteUser(userIdx);
                return new PostUserRes(result);
            }
            else
            {
                throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
            }
        }catch (BaseException exception){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
