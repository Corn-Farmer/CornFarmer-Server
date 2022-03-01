package com.farmer.cornfarmer.src.Entity;

import com.farmer.cornfarmer.src.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="review")
public class ReviewEntity {
    @Id
    @Column(name = "review_idx", nullable = false)
    private Long review_idx;

    private Long movie_idx;

    private Long user_idx;

    @Column
    private String contents;

    @Column
    private Long rate;

    @Column
    private Long like_cnt;

    @Column
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date updated_at;

    @ManyToMany(mappedBy = "reviews")
    private List<UserEntity> users;

}
