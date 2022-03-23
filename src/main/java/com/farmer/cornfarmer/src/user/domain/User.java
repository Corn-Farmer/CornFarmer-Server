package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.user.enums.ActiveType;
import com.farmer.cornfarmer.src.user.enums.Gender;
import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import lombok.*;

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

    @Builder(access = AccessLevel.PACKAGE)
    private User(String oauthId, UserSocialType oauthChannel, String nickname, String photo,
                 Gender gender, ActiveType active, Date birth) {
        this.socialInfo = SocialInfo.of(oauthId, oauthChannel);
        this.nickname = nickname;
        this.photo = photo;
        this.gender = gender;
        this.active = active;
        this.birth = birth;
    }

    public static User of(String oauthId, UserSocialType oauthChannel, String nickname, String photo,
                          Gender gender, Date birth) {
        return User.builder()
                .oauthId(oauthId)
                .oauthChannel(oauthChannel)
                .nickname(nickname)
                .photo(photo)
                .gender(gender)
                .active(ActiveType.ACTIVE)
                .birth(birth)
                .build();
    }
}
