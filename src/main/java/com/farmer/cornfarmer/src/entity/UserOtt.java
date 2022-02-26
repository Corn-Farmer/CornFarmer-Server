package com.farmer.cornfarmer.src.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_ott")
public class UserOtt {
    @Id
    @GeneratedValue
    private int idx;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx",nullable = false)
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ott_idx",nullable = false)
    private List<Ott> otts = new ArrayList<>();

}
