package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.user.model.GetMyReviewRes;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.utils.ValidationRegex;
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

}
