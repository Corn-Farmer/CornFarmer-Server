package com.farmer.cornfarmer.src.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private int user_idx;

    private String oauth_channel;

    private String oauth_id;

    private String nickname;

    private String photo;

    @ColumnDefault(value = "0")
    private Boolean is_male;

    private Date birth;

    @ColumnDefault(value = "0")
    private Boolean active;
}
