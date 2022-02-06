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
import com.farmer.cornfarmer.utils.S3Uploader;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    private final S3Uploader S3Uploader;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, S3Uploader S3Uploader) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.S3Uploader = S3Uploader;
    }



    /**
     * 카카오 로그인
     * [GET] /users/oauth/kakao
     * 개발자 : 팡코(조대환)
     */
    @ResponseBody
    @PostMapping("/oauth/kakao")
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestBody PostLoginReq postLoginReq) throws BaseException { //카카오 엑세스토큰 받아옴
        String cornfarmer = "";
        String accessToken = postLoginReq.getAccessToken();
        System.out.println("accessToken(kakaoLogin) : " + accessToken);
        try {
            String id = userService.getKakaoOauthId(accessToken);
            if (userProvider.checkOauthId(id)) {
                //db에 존재하는경우 ->login
                if(!Objects.equals(userProvider.checkUserNickname(id), cornfarmer)) {
                    //회원가입이 완료된 경우
                    PostLoginRes postLoginRes = userProvider.kakaoLogIn(id);
                    return new BaseResponse<>(postLoginRes);
                }
                else
                {
                    //oautid는 저장됐지만 회원가입은 안한경우
                    PostLoginRes postLoginRes = new PostLoginRes(true, userService.emptyJwt(id), userProvider.getUserIdx(id));
                    return new BaseResponse<>(postLoginRes);
                }
            } else {
                //db에 oauthid 존재하지 않는경우 디비에 삽입하고 리턴
                int userIdx = userService.createUser(id,"kakao");
                PostLoginRes postLoginRes = new PostLoginRes(true, userService.emptyJwt(id), userIdx);
                return new BaseResponse<>(postLoginRes);
            }
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 네이버 로그인
     * [GET] /users/oauth/naver
     * 개발자 : 팡코(조대환)
     */
    @ResponseBody
    @PostMapping("/oauth/naver")
    public BaseResponse<PostLoginRes> naverLogin(@RequestBody PostLoginReq postLoginReq) throws BaseException { //카카오 엑세스토큰 받아옴
        String cornfarmer = "";
        String accessToken = postLoginReq.getAccessToken();
        System.out.println("accessToken(kakaoLogin) : " + accessToken);
        try {
            String id = userService.getNaverOauthId(accessToken);
            if (userProvider.checkOauthId(id)) {
                //db에 존재하는경우 ->login
                if(!Objects.equals(userProvider.checkUserNickname(id), cornfarmer)) {
                    //회원가입이 완료된 경우
                    PostLoginRes postLoginRes = userProvider.naverLogIn(id);
                    return new BaseResponse<>(postLoginRes);
                }
                else
                {
                    //oautid는 저장됐지만 회원가입은 안한경우
                    PostLoginRes postLoginRes = new PostLoginRes(true, userService.emptyJwt(id), userProvider.getUserIdx(id));
                    return new BaseResponse<>(postLoginRes);
                }
            } else {
                //db에 oauthid 존재하지 않는경우 디비에 삽입하고 리턴
                int userIdx = userService.createUser(id,"naver");
                PostLoginRes postLoginRes = new PostLoginRes(true, userService.emptyJwt(id), userIdx);
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
    public BaseResponse<PostUserRes> join(@ModelAttribute PostUserReq postUserReq) throws BaseException{
        try{
            //kakao naver.
            String ouath_id = jwtService.getOauthId();
            if(userProvider.checkOauthId(ouath_id) == false)
            {
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }
            if(userProvider.duplicateNick(postUserReq.getNickname()))
            {
                throw new BaseException(BaseResponseStatus.DUPLICATE_NICKNAME);
            }
            String PhotoUrl = S3Uploader.upload(postUserReq.getPhoto(),"user");
            PostUserRes postUserRes = userService.createUserInfo(postUserReq, PhotoUrl,ouath_id);
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
    public BaseResponse<UserMyInfo> modifyMyInfo(@PathVariable int userIdx, @ModelAttribute PostUserInfoReq postUserInfoReq){
        try{
            int tokenIdx = jwtService.getUserIdx();
            if(userIdx == tokenIdx) {
                //이전에 저장되어있던 사진파일 삭제
                if(userProvider.checckDuplicateNick(postUserInfoReq.getNickname(), userIdx))
                {
                    throw new BaseException(BaseResponseStatus.DUPLICATE_NICKNAME);
                }
                String PhotoUrl = S3Uploader.upload(postUserInfoReq.getPhoto(), "user");
                return new BaseResponse<>(userService.modifyMyInfo(userIdx, postUserInfoReq, PhotoUrl));
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
        try {
            int tokenIdx = jwtService.getUserIdx();
            if (userIdx == tokenIdx) {
                PostUserRes userRes = userService.inactive(userIdx);
                return new BaseResponse<>(userRes);
            }
            else
            {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
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
    public BaseResponse<List<GetMyReviewRes>> getMyReviews(@PathVariable int userIdx, @RequestParam(name="sort", defaultValue = "recent") String sort ){
        try{
            //int userJwtIdx = jwtService.getUserIdx();
            int userJwtIdx = 1; //가정
            List<GetMyReviewRes> result;
            switch(sort) {
                case "recent":
                    result = userProvider.getMyReviews(userIdx, userJwtIdx,"created_at");
                    break;
                case "like":
                    result = userProvider.getMyReviews(userIdx, userJwtIdx,"r.like_cnt");
                    break;
                case "rate":
                    result = userProvider.getMyReviews(userIdx, userJwtIdx,"rate");
                    break;
                default:
                    result = null;
            }

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
