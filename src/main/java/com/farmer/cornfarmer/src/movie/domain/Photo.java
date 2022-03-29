package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(PhotoPK.class)
@Table(name = "movie_photo")
public class Photo {
    @Id
    @ManyToOne
    @JoinColumn(name = "movie_idx")
    private Movie movie;

    @Id
    @Column(name = "photo")
    private String photo;
}
