package com.farmer.cornfarmer.src.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ott")
public class OttEntity {
    @Id
    @Column(name = "ott_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ott_idx;

    @Column
    private String name;

    @Column
    private String photo;
}
