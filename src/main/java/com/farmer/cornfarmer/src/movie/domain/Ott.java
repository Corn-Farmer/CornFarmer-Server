package com.farmer.cornfarmer.src.movie.domain;

import com.farmer.cornfarmer.src.user.domain.UserLikeOtt;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ott {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ottIdx;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String photo;

    @OneToMany(mappedBy = "ott")
    private List<UserLikeOtt> ottLikedByUserList = new ArrayList<>();
}
