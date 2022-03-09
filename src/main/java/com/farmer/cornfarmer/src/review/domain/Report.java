package com.farmer.cornfarmer.src.review.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportIdx;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_idx")
    private Review review;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User writer;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    private Timestamp created_at;
}
