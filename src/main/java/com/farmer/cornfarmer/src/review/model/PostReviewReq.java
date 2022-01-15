package com.farmer.cornfarmer.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {

    @NotNull
    @Positive
    //movieIdx가 NULL이거나 음수 또는 0이면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private int movieIdx;

    @NotBlank
    //content가 NULL이거나 문자가 하나 이상 포함되지 않으면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private String content;

    @NotNull
    @PositiveOrZero
    //float이 NULL이거나 음수이면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private float rate;
}
