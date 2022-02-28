package com.farmer.cornfarmer.src.user.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender {

    MALE("남성"),
    FEMALE("여성")
    ;

    private final String description;

}
