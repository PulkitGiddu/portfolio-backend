package com.portfolio.repository;

import com.portfolio.model.PageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageViewRepository extends JpaRepository<PageView, Long> {
    List<PageView> findAllByOrderByTimestampDesc();
}
