package com.farmer.cornfarmer.src.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idx;

    Long userIdx;

    public UserReport(long userIdx){
        this.userIdx = userIdx;
    }
}
