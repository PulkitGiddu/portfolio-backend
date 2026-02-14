package com.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "page_views")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pagePath;

    private String ipAddress;

    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
