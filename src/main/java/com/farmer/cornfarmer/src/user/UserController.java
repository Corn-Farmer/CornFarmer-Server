package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 카카오 로그인
     * [GET] /users/outh/kakao
     * 개발자 : 팡코(조대환)
     */
    @ResponseBody
    @GetMapping("/outh/kakao")
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestParam String accessToken) throws BaseException { //카카오 엑세스토큰 받아옴
        try {
            String id = userService.getKakaoOauthId(accessToken);
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
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 네이버 로그인
     * [GET] /users/outh/naver
     * 개발자 : 팡코(조대환)
     */
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
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 회원가입
     * [POST] /users
     * 개발자 : 팡코(조대환)
     */
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

    /**
     * 나의 정보보기
     * [GET] /users/{userIdx}
     * 개발자 : 팡코(조대환)
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<UserMyInfo> getMyInfo(@PathVariable int userIdx){
        try{
            int tokenIdx = jwtService.getUserIdx();
            if(userIdx == tokenIdx) {
                return new BaseResponse<>(userProvider.getMyInfo(userIdx));
            }
            else
            {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 나의 정보수정
     * [POST] /users/{userIdx}
     * 개발자 : 팡코(조대환)
     */
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<UserMyInfo> modifyMyInfo(@PathVariable int userIdx, @RequestBody PostUserInfoReq postUserInfoReq){
        try{
            int tokenIdx = jwtService.getUserIdx();
            if(userIdx == tokenIdx) {
                return new BaseResponse<>(userService.modifyMyInfo(userIdx, postUserInfoReq));
            }
            else
            {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
    /**
     * 회원탈퇴
     * [POST] /users/{userIdx}/delete
     * 개발자 : 팡코(조대환)
     */

    @ResponseBody
    @PutMapping("/{userIdx}/delete")
    public BaseResponse<PostUserRes> deleteUser(@PathVariable int userIdx){
        try{
            PostUserRes userRes = userService.inactive(userIdx);
            return new BaseResponse<>(userRes);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 나의 후기 모두 보기
     * [GET] /users/{userIdx}/reviews
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @GetMapping("/{userIdx}/reviews")
    public BaseResponse<List<GetMyReviewRes>> getMyReviews(@PathVariable int userIdx ){
        try{
            //int userJwtIdx = jwtService.getUserIdx();
            int userJwtIdx = 1; //가정
            List<GetMyReviewRes> result = userProvider.getMyReviews(userIdx,userJwtIdx);
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 찜한 작품 모두 보기
     * [GET] /users/{userIdx}/likes/movies
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @GetMapping("/{userIdx}/likes/movies")
    public BaseResponse<List<GetMyMovieLikedRes>> getMyMoviesLiked(@PathVariable int userIdx ){
        try{
            //int userJwtIdx = jwtService.getUserIdx();
            int userJwtIdx = 1; //가정
            List<GetMyMovieLikedRes> result = userProvider.getMyMoviesLiked(userIdx,userJwtIdx);
            return new BaseResponse<>(result);
        }catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
