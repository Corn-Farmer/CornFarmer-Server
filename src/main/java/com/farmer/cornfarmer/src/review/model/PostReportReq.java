package com.farmer.cornfarmer.src.review.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class PostReportReq {

    @NotBlank(message = "content 값이 유효하지 않습니다.")
    //report가 NULL이거나 문자가 하나 이상 포함되지 않으면 ExceptionAdvisor가 REQUEST_ERROR에러를 발생시킨다.
    private String report;

    private boolean reportUser; //사용자 신고 여부

    private boolean banUser;    //사용자 차단 여부
}
