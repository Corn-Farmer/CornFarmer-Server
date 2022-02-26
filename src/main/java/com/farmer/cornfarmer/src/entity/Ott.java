package com.farmer.cornfarmer.src.entity;

import javax.persistence.*;

@Entity
@Table(name = "ott")
public class Ott {
    @Id
    @GeneratedValue
    int ott_idx;

    @Column(nullable = false)
    private String name;

    private String photo;
}
