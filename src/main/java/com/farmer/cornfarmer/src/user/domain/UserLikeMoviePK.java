package com.farmer.cornfarmer.src.user.domain;

import com.farmer.cornfarmer.src.movie.domain.Movie;

import javax.persistence.*;
import java.io.Serializable;

public class UserLikeMoviePK implements Serializable {
    private User user;

    private Movie movie;
}
