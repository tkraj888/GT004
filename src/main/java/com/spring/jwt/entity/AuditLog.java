package com.spring.jwt.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @Column(nullable = false)
    private String event; // e.g., "Stock Updated", "User Logged In"

    @Column(nullable = false)
    private String performedBy; // Username or user ID

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 255)
    private String details; // Additional information
}
