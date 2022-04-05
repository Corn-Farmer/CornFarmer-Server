package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.review.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtService jwtService;

    /**
     * 후기 작성 API
     * [POST] /reviews
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PostMapping()    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostReviewRes> postReview(@RequestBody @Valid PostReviewReq postReviewReq) {
        try {
            long userIdx = jwtService.getUserIdx();
            if (userIdx == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_JWT);
            }
            PostReviewRes postReviewRes = reviewService.createReview(userIdx, postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch (BaseException exception) {
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
    public <T> BaseResponse<T> putReview(@PathVariable long reviewIdx, @RequestBody @Valid PutReviewReq putReviewReq) {
        try {
            long userIdx = jwtService.getUserIdx();
            if (userIdx == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_JWT);
            }
            reviewService.modifyReview(reviewIdx, userIdx, putReviewReq);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
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
    public <T> BaseResponse<T> deleteReview(@PathVariable long reviewIdx) {
        try {
            long userIdx = jwtService.getUserIdx();
            if (userIdx == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_JWT);
            }
            reviewService.deleteReview(reviewIdx, userIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 좋아요 API
     * [POST] /reviews/{reviewIdx}/like
     * 개발자: 제트(김예지)
     */
    @ResponseBody
    @PutMapping("/{reviewIdx}/like")
    public BaseResponse<PutLikeReviewRes> putLikeReview(@PathVariable long reviewIdx) {
        try {
            long userIdx = jwtService.getUserIdx();
            if (userIdx == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_JWT);
            }
            PutLikeReviewRes putLikeReviewRes = reviewService.likeReview(reviewIdx, userIdx);
            return new BaseResponse<>(putLikeReviewRes);
        } catch (BaseException exception) {
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
    public BaseResponse<PostReportRes> postReviewReport(@PathVariable long reviewIdx, @RequestBody @Valid PostReportReq postReportReq) {
        try {
            long userIdx = jwtService.getUserIdx();
            if (userIdx == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_JWT);
            }
            PostReportRes postReportRes = reviewService.createReport(reviewIdx, userIdx, postReportReq);
            return new BaseResponse<>(postReportRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
