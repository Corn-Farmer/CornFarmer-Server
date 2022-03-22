package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.src.review.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
