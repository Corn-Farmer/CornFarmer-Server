package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.secret.Secret;
import com.farmer.cornfarmer.utils.AES128;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

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

    public int getKakaoOauthId(String accessToken) throws BaseException {
        //access token 으로 oauth_id 가져오기
        int id = 0;
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
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
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }

        return id;
    }


}
