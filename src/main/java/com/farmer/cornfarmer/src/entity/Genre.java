package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue
    private int genre_idx;

    @Column(nullable = false)
    private String genre_name;
}
