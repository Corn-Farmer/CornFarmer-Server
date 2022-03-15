package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.user.enums.ActiveType;
import com.farmer.cornfarmer.src.user.enums.Gender;
import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Embedded
    private SocialInfo socialInfo;

    @Column(length = 6)
    private String nickname;

    @Column(length = 1000)
    private String photo;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private ActiveType active;

    @Temporal(TemporalType.DATE)
    private Date birth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLikeReview> userLikeReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLikeOtt> userLikeOttList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLikeMovie> userLikeMovieList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLikeGenre> userLikeGenreList = new ArrayList<>();

}
