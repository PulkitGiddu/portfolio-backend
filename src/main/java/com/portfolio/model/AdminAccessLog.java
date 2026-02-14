package com.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_access_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String status; // SUCCESS, FAILURE
}
