package com.farmer.cornfarmer.src.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue
    private int report_idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_idx", nullable = false)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx", nullable = false)
    private List<User> users = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    private Timestamp created_at;
}
