package com.farmer.cornfarmer.src.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserInfo {
    private int user_idx;
    private String oauth_channel;
    private int oauth_id;
    private String nickname;
}
