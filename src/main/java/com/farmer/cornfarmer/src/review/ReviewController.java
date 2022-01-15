package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.review.model.PutReviewReq;
import com.farmer.cornfarmer.src.review.model.PostReviewReq;
import com.farmer.cornfarmer.src.review.model.PostReviewRes;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;


    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 리뷰 생성 API
     * [POST] /reviews
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PostMapping()    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostReviewRes> createReview(@RequestBody @Valid PostReviewReq postReviewReq) {
        try{
            //int userIdx = jwtService.getUserIdx();
            int userIdx = 1; //가정
            PostReviewRes postReviewRes = reviewService.createReview(userIdx,postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch(BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 리뷰 수정 API
     * [PUT] /reviews/{reviewIdx}
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PutMapping("/{reviewIdx}")
    public BaseResponse modifyReview(@PathVariable int reviewIdx, @RequestBody @Valid PutReviewReq putReviewReq){
        try{
            //int userIdx = jwtService.getUserIdx();
            int userIdx = 1; //가정
            int reviewUserIdx = reviewProvider.getUserIdx(reviewIdx);    //수정하려는 후기의 userIdx와 일치하는지 확인
            if(userIdx != reviewUserIdx){
                return new BaseResponse(BaseResponseStatus.INVALID_USER_JWT);
            }
            reviewService.modifyReview(reviewIdx,putReviewReq);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }catch(BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 리뷰 삭제 API
     * [DELETE] /reviews/{reviewIdx}
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @DeleteMapping("/{reviewIdx}")
    public BaseResponse deleteReview(@PathVariable int reviewIdx){
        try{
            //int userIdx = jwtService.getUserIdx();
            int userIdx = 1; //가정
            int reviewUserIdx = reviewProvider.getUserIdx(reviewIdx);    //삭제하려는 후기의 userIdx와 일치하는지 확인
            if(userIdx != reviewUserIdx){
                return new BaseResponse(BaseResponseStatus.INVALID_USER_JWT);
            }
            reviewService.deleteReview(reviewIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch(BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
