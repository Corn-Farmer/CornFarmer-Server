package com.farmer.cornfarmer.src.movie.domain;

import javax.persistence.*;
import java.io.Serializable;


public class KeywordMoviePK implements Serializable {

    private Movie movie;

    private Keyword keyword;
}