package com.farmer.cornfarmer.src.review.model;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@RequiredArgsConstructor
public class PutReviewReq {

    @NotBlank(message = "content 값이 유효하지 않습니다.")
    @Size(min = 5, message = "5 글자 이상 입력해주세요.")
    //content가 NULL이거나 문자가 하나 이상 포함되지 않으면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private String content;

    @NotNull(message = "rate 값이 유효하지 않습니다.")
    @DecimalMax(value = "5.0", message = "5.0 이하의 평점을 입력해주세요.")
    @DecimalMin(value = "1.0", message = "1.0 이상의 평점을 입력해주세요.")
    //float이 NULL이거나 음수이면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private float rate;

}
