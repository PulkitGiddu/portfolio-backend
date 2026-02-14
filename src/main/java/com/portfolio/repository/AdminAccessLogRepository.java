package com.portfolio.repository;

import com.portfolio.model.AdminAccessLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminAccessLogRepository extends JpaRepository<AdminAccessLog, Long> {
    List<AdminAccessLog> findAllByOrderByTimestampDesc();
}
