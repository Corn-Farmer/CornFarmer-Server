package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.src.user.domain.PostLoginRes;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<PostLoginRes> KakaoLogin(@RequestParam String accessToken) throws BaseException {
        int oauth_id = userService.getKakaoOauthId(accessToken);

        if(userProvider.checkOauthId(oauth_id)) {
            PostLoginRes postLoginRes = userProvider.kakaoLogIn(oauth_id);
            return new BaseResponse<>(postLoginRes);
        }
        else
        { //존재하지 않는경우
            PostLoginRes postLoginRes = new PostLoginRes(true, null, -1);
            return new BaseResponse<>(postLoginRes);
        }
    }
}
