package com.farmer.cornfarmer.utils;


import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.secret.Secret;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.farmer.cornfarmer.config.BaseResponseStatus.*;

@Service
public class JwtService {

    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(int user_idx, String oauth_channel, int oauth_id, String nickname){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("user_idx",user_idx)
                .claim("oauth_channel",oauth_channel)
                .claim("oauth_id", oauth_id)
                .claim("nickname", nickname)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    public boolean checkJwt(String jwt) throws Exception {
        try {
            String accessToken = getJwt();
            Claims claims = Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰

            System.out.println("expireTime :" + claims.getExpiration());
            System.out.println("name :" + claims.get("name"));
            System.out.println("Email :" + claims.get("email"));

            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("토큰 만료");
            return false;
        } catch (JwtException exception) {
            System.out.println("토큰 변조");
            return false;
        }
    }
    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("user_idx",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }

    public String getOauthChannel() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. oauth_channel 추출
        return claims.getBody().get("oauth_channel",String.class);  // jwt 에서 userIdx를 추출합니다.
    }

    public int getOauthId() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. oauth_id 추출
        return claims.getBody().get("oauth_id",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }
    public String getNickname() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. nickname 추출
        return claims.getBody().get("nickname",String.class);  // jwt 에서 userIdx를 추출합니다.
    }
}
