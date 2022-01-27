package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserInfo {
    private int user_idx;
    private String oauth_channel;
    private String oauth_id;
    private String nickname;
}
