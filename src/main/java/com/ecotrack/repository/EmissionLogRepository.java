package com.ecotrack.repository;

import com.ecotrack.entity.EmissionLog;
import com.ecotrack.entity.EmissionLog.EmissionStatus;
import com.ecotrack.entity.EmissionLog.EmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmissionLogRepository extends JpaRepository<EmissionLog, Long> {

    // ─────────────────────────────────────
    // Find by single field
    // ─────────────────────────────────────

    // Get all logs of a specific industry
    List<EmissionLog> findByIndustryId(Long industryId);

    // Get all logs of a specific type (CO2/NOx/SOx)
    List<EmissionLog> findByType(EmissionType type);

    // Get all logs by status (PENDING/APPROVED/REJECTED/FLAGGED)
    List<EmissionLog> findByStatus(EmissionStatus status);

    // ─────────────────────────────────────
    // Find by multiple fields
    // ─────────────────────────────────────

    // Get logs of a specific industry with specific status
    List<EmissionLog> findByIndustryIdAndStatus(Long industryId, EmissionStatus status);

    // Get logs of a specific industry with specific type
    List<EmissionLog> findByIndustryIdAndType(Long industryId, EmissionType type);

    // ─────────────────────────────────────
    // Find by date range
    // ─────────────────────────────────────

    // Get logs between two dates
    List<EmissionLog> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Get logs of specific industry between two dates
    List<EmissionLog> findByIndustryIdAndDateBetween(
            Long industryId,
            LocalDate startDate,
            LocalDate endDate
    );
}