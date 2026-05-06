package com.internship.tool.repository;

import com.internship.tool.entity.Compliance;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ComplianceRepository extends JpaRepository<Compliance, Long> {

    // =========================
    // PERFORMANCE OPTIMIZATION
    // Prevent N+1 problem
    // =========================
    @EntityGraph(attributePaths = {})
    List<Compliance> findByStatus(String status);

    // =========================
    // OPTIMIZED CUSTOM QUERY
    // =========================
    @Query("""
        SELECT c
        FROM Compliance c
        WHERE c.status = :status
    """)
    List<Compliance> findAllByStatus(@Param("status") String status);

    // =========================
    // DUE DATE QUERIES
    // =========================
    List<Compliance> findByDueDateIsNotNullAndDueDateBeforeAndStatusNot(
            LocalDate date,
            String status
    );

    List<Compliance> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    List<Compliance> findByDueDateBetween(
            LocalDate start,
            LocalDate end
    );

    List<Compliance> findByDueDateBefore(LocalDate date);

    // =========================
    // DATE RANGE
    // =========================
    @Query("""
        SELECT c
        FROM Compliance c
        WHERE c.createdAt BETWEEN :start AND :end
    """)
    List<Compliance> findByDateRange(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // =========================
    // SEARCH
    // =========================
    List<Compliance> findByTitleContainingIgnoreCaseAndDeletedFalse(String q);

    // =========================
    // STATS
    // =========================
    @Query("""
        SELECT COUNT(c)
        FROM Compliance c
        WHERE c.deleted = false
    """)
    long countActive();

    @Query("""
        SELECT COUNT(c)
        FROM Compliance c
        WHERE c.status = 'OPEN'
        AND c.deleted = false
    """)
    long countOpen();

    @Query("""
        SELECT COUNT(c)
        FROM Compliance c
        WHERE c.status = 'CLOSED'
        AND c.deleted = false
    """)
    long countClosed();
}