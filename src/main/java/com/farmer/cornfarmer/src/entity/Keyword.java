package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;

@Entity
@Table(name = "keyword")
public class Keyword {
    @Id
    @GeneratedValue
    private int keyword_idx;

    @Column(nullable = false)
    private String keyword;
}
