package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.review.model.GetReviewRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ReviewAdminController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewService reviewService;
    /**
     * 모든 후기 보기 (관리자용)
     * [GET] /admin/reviews
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @GetMapping("/reviews")
    public BaseResponse<List<GetReviewRes>> getAllReviews() {
        try {
            //admin 계정인지 검증
            List<GetReviewRes> result = reviewService.getAllReviewsAdmin();
            return new BaseResponse(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 삭제 (관리자용) API
     * [DELETE] /admin/reviews/{reviewId}
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @DeleteMapping("/reviews/{reviewIdx}")
    public BaseResponse deleteReview(@PathVariable long reviewIdx) {
        try {
            //admin 계정인지 검증
            reviewService.deleteReviewAdmin(reviewIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
