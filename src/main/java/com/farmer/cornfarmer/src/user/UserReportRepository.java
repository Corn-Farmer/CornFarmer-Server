package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
