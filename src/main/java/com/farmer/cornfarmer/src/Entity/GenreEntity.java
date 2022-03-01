package com.farmer.cornfarmer.src.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="genre")
public class GenreEntity {
    @Id
    @Column(name = "genre_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genre_idx;

    @Column
    private String genre_name;

}
