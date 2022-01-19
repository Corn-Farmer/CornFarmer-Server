package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.domain.PostLoginRes;
import com.farmer.cornfarmer.src.user.domain.PostUserReq;
import com.farmer.cornfarmer.src.user.domain.PostUserRes;
import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * API 설명
     * [HTTP METHOD] URL
     * 개발자 : 이름
     */
    /**
     * 카카오 로그인
     * [HTTP METHOD] /users/outh/kakao
     * 개발자 : 팡코(조대환)
     */
    @ResponseBody
    @GetMapping("/outh/kakao")
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestParam String accessToken) throws BaseException { //카카오 엑세스토큰 받아옴
        try {
            String id = Integer.toString(userService.getKakaoOauthId(accessToken));
            if (userProvider.checkOauthId(id)) {
                //db에 존재하는경우 ->login
                if(userProvider.checkNickname(id)) { 
                    //회원가입이 완료된 경우
                    PostLoginRes postLoginRes = userProvider.kakaoLogIn(id);
                    return new BaseResponse<>(postLoginRes);
                }
                else
                {
                    //oautid는 저장됐지만 회원가입은 안한경우
                    PostLoginRes postLoginRes = new PostLoginRes(true, id, userProvider.getUserIdx(id));
                    return new BaseResponse<>(postLoginRes);
                }
            } else {
                //db에 oauthid 존재하지 않는경우 디비에 삽입하고 리턴
                int userIdx = userService.createUser(id,"kakao");
                PostLoginRes postLoginRes = new PostLoginRes(true, id, userIdx);
                return new BaseResponse<>(postLoginRes);
            }
        }
        catch (BaseException exception){
            return new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    @ResponseBody
    @GetMapping("/outh/naver")
    public BaseResponse<PostLoginRes> naverLogin(@RequestParam String accessToken) throws BaseException { //카카오 엑세스토큰 받아옴
        try {
            String id = userService.getNaverOauthId(accessToken);
            if (userProvider.checkOauthId(id)) {
                //db에 존재하는경우 ->login
                if(userProvider.checkNickname(id)) {
                    //회원가입이 완료된 경우
                    PostLoginRes postLoginRes = userProvider.naverLogIn(id);
                    return new BaseResponse<>(postLoginRes);
                }
                else
                {
                    //oautid는 저장됐지만 회원가입은 안한경우
                    PostLoginRes postLoginRes = new PostLoginRes(true, id, userProvider.getUserIdx(id));
                    return new BaseResponse<>(postLoginRes);
                }
            } else {
                //db에 oauthid 존재하지 않는경우 디비에 삽입하고 리턴
                int userIdx = userService.createUser(id,"naver");
                PostLoginRes postLoginRes = new PostLoginRes(true, id, userIdx);
                return new BaseResponse<>(postLoginRes);
            }
        }
        catch (BaseException exception){
            return new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

/*    @DeleteMapping("/outh/logout")
    public BaseResponse<Optional> logOut(){


        return new BaseResponse<>(null);
    }*/
    @ResponseBody
    @PostMapping("/")
    public BaseResponse<PostUserRes> join(@RequestBody PostUserReq postUserReq) throws BaseException{
        try{
            //kakao naver.
            if(userProvider.checkOauthId(postUserReq.getOauth_id()) == false)
            {
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }
            PostUserRes postUserRes = userService.createUserInfo(postUserReq);
            return new BaseResponse<>(postUserRes);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
