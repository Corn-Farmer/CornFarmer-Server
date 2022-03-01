package com.farmer.cornfarmer.src.Entity;

import com.farmer.cornfarmer.src.movie.model.Ott;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="movie")
public class MovieEntity {
    @Id
    @Column(name = "movie_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_idx;

    @Column
    private String movie_title;

    @Column
    private String release_year;

    @Column
    private String synopsis;

    @Column
    private String director;

    @Column
    private int like_cnt;

    @ManyToMany(mappedBy = "movies")
    private List<KeywordEntity> keywords = new ArrayList<KeywordEntity>();

    @ManyToMany //양방향
    @JoinTable(name="movie_genre",
            joinColumns = @JoinColumn(name = "movie_idx"),
            inverseJoinColumns = @JoinColumn(name="genre_idx"))
    private List<GenreEntity> Genres = new ArrayList<GenreEntity>();

    @ManyToMany //양방향
    @JoinTable(name="movie_ott",
            joinColumns = @JoinColumn(name = "movie_idx"),
            inverseJoinColumns = @JoinColumn(name="ott_idx"))
    private List<OttEntity> otts = new ArrayList<OttEntity>();

    @OneToMany //양방향
    @JoinTable(name="movie_review",
            joinColumns = @JoinColumn(name = "movie_idx"),
            inverseJoinColumns = @JoinColumn(name="review_idx"))
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();


}
