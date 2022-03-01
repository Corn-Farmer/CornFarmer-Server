package com.farmer.cornfarmer.src.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_idx;

    @Column
    private String oauth_channel;

    @Column
    private String oauth_id;

    @Column
    private String nickname;

    @Column
    private String photo;

    @Column
    private boolean is_male;

    @Temporal(TemporalType.DATE)
    @Column
    private Date birth;

    @Column
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date updated_at;

    @ManyToMany
    @JoinTable(name="user_ott",
    joinColumns = @JoinColumn(name = "user_idx"),
    inverseJoinColumns = @JoinColumn(name="ott_idx"))
    private List<OttEntity> otts = new ArrayList<OttEntity>();


    @ManyToMany //양방향
    @JoinTable(name="user_review",
            joinColumns = @JoinColumn(name = "user_idx"),
            inverseJoinColumns = @JoinColumn(name="review_idx"))
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();

    @ManyToMany
    @JoinTable(name="user_genre",
            joinColumns = @JoinColumn(name = "user_idx"),
            inverseJoinColumns = @JoinColumn(name="genre_idx"))
    private List<GenreEntity> genres = new ArrayList<GenreEntity>();

    @ManyToMany
    @JoinTable(name="user_movie",
            joinColumns = @JoinColumn(name = "user_idx"),
            inverseJoinColumns = @JoinColumn(name="movie_idx"))
    private List<MovieEntity> movies = new ArrayList<MovieEntity>();
}
