package com.farmer.cornfarmer.src.review.domain;

import com.farmer.cornfarmer.src.common.domain.BaseTimeEntity;
import com.farmer.cornfarmer.src.review.model.PostReportReq;
import com.farmer.cornfarmer.src.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportIdx;

    @ManyToOne(targetEntity = Review.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_idx")
    private Review review;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User writer;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    private Timestamp created_at;

    @Builder
    public Report (Review review, User user, PostReportReq postReportReq) {
        this.review = review;
        this.writer = user;
        this.contents = postReportReq.getReport();
    }
}
