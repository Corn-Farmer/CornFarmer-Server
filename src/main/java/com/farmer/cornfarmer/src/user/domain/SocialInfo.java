package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SocialInfo {

    @Column(nullable = false, length = 200)
    private String oauthId;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserSocialType oauthChannel;

    private SocialInfo(String oauthId, UserSocialType oauthChannel) {
        this.oauthId = oauthId;
        this.oauthChannel = oauthChannel;
    }

    public static SocialInfo of(String socialId, UserSocialType socialType) {
        return new SocialInfo(socialId, socialType);
    }

}
