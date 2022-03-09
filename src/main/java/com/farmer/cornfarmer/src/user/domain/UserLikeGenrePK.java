package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Genre;

import javax.persistence.*;
import java.io.Serializable;


public class UserLikeGenrePK implements Serializable{

    private User user;

    private Genre genre;
}