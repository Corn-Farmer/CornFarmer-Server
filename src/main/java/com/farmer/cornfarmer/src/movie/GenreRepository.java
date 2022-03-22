package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.src.movie.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findGenreByGenreIdx(Long genre_idx);
}
