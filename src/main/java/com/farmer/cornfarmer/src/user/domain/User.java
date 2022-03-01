package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.user.enums.Gender;
import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
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

    private String photo;

    /**
     *     int를 Gender로 바꾸고싶어요.......!!!!!!
     *     @Enumerated(EnumType.STRING)
     *     private Gender gender;
     */
    private int is_male;

    /**
     *     int를 AccessType로 바꾸고싶어요.......!!!!!!
     *     @Enumerated(EnumType.STRING)
     *     private AccessType active;
     */
    private int active;

    @Temporal(TemporalType.DATE)
    private Date birth;

    @OneToMany(mappedBy = "user")
    private List<UserLikeReview> userLikeReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserLikeOtt> userLikeOttList = new ArrayList<>();
}
