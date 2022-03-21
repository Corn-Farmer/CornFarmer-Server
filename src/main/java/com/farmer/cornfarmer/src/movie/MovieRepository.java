package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.src.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
