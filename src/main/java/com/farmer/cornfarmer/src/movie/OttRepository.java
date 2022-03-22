package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.src.movie.domain.Ott;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OttRepository extends JpaRepository<Ott, Long> {
    Ott findOttByOttIdx(Long ott_idx);
}
