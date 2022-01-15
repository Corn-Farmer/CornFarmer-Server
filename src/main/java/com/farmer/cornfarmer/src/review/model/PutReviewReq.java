package com.farmer.cornfarmer.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
public class PutReviewReq {

    @NotBlank(message = "content 값이 유효하지 않습니다.")
    //content가 NULL이거나 문자가 하나 이상 포함되지 않으면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private String content;

    @NotNull(message = "rate 값이 유효하지 않습니다.")
    @PositiveOrZero
    @Max(value = 5)
    //float이 NULL이거나 음수이면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private float rate;

}
