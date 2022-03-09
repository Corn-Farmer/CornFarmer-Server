package com.farmer.cornfarmer.src.movie.domain;

import javax.persistence.*;
import java.io.Serializable;

public class MovieGenrePK implements Serializable {

    private Movie movie;

    private Genre genre;
}