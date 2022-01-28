package com.farmer.cornfarmer.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int user_idx;
    private String oauth_channel;
    private int oauth_id;
    private String photo;
    private String nickname;
    private boolean is_male;
    private Date birth;

}
