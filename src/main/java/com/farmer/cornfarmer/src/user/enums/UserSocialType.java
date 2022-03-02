package com.farmer.cornfarmer.src.user.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserSocialType {

    KAKAO("카카오톡"),
    NAVER("네이버")
    ;

    private final String description;

}

