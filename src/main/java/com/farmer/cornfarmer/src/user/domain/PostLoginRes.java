package com.farmer.cornfarmer.src.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes {
    private boolean isNew;
    private String id;
    private int userIdx;
}
