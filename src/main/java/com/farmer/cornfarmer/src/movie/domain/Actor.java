package com.farmer.cornfarmer.src.movie.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Actor {

    //TODO : 데이터베이스 상에서 actor_idx가 movie_idx로 되어있음 => 추후에 유지보수 배포전에 바꿔야함

    @Id
    @Column(name = "movie_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorIdx;

    @Column(name = "actor_name", nullable = false)
    private String name;
}
