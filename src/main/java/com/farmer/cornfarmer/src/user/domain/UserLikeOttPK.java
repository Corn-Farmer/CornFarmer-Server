package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Ott;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

public class UserLikeOttPK implements Serializable {

    private User user;

    private Ott ott;
}