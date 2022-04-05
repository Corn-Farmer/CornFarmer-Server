package com.farmer.cornfarmer.src.review.repository;

import com.farmer.cornfarmer.src.review.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
