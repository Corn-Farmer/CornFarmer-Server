package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.review.model.*;
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
     * 후기 작성 API
     * [POST] /reviews
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PostMapping()    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostReviewRes> postReview(@RequestBody @Valid PostReviewReq postReviewReq) {
        try{
            int userIdx = jwtService.getUserIdx();
            PostReviewRes postReviewRes = reviewService.createReview(userIdx,postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 수정 API
     * [PUT] /reviews/{reviewIdx}
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PutMapping("/{reviewIdx}")
    public BaseResponse putReview(@PathVariable int reviewIdx, @RequestBody @Valid PutReviewReq putReviewReq){
        try{
            int userIdx = jwtService.getUserIdx();
            reviewService.modifyReview(reviewIdx,userIdx,putReviewReq);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 삭제 API
     * [DELETE] /reviews/{reviewIdx}
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @DeleteMapping("/{reviewIdx}")
    public BaseResponse deleteReview(@PathVariable int reviewIdx){
        try{
            int userIdx = jwtService.getUserIdx();
            reviewService.deleteReview(reviewIdx,userIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 좋아요 API
     * [POST] /reviews/{reviewIdx}/like
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PostMapping("/{reviewIdx}/like")
    public BaseResponse postLikeReview(@PathVariable int reviewIdx){
        try{
            int userIdx = jwtService.getUserIdx();
            reviewService.likeReview(reviewIdx,userIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 신고 API
     * [POST] /reviews/{reviewIdx}/report
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PostMapping("/{reviewIdx}/report")
    public BaseResponse<PostReportRes> postReviewReport(@PathVariable int reviewIdx, @RequestBody @Valid PostReportReq postReportReq){
        try{
            int userIdx = jwtService.getUserIdx();
            PostReportRes postReportRes = reviewService.createReviewReport(reviewIdx,userIdx,postReportReq);
            return new BaseResponse<>(postReportRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
