package com.ecotrack.service;

import com.ecotrack.dto.EmissionLogDTO;
import com.ecotrack.entity.EmissionLog;
import com.ecotrack.entity.EmissionLog.EmissionStatus;
import com.ecotrack.entity.EmissionLog.EmissionType;
import com.ecotrack.repository.EmissionLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmissionLogService {

    private final EmissionLogRepository repository;

    // ─────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────
    @Transactional
    public EmissionLogDTO.Response create(EmissionLogDTO.Request request) {
        EmissionLog log = EmissionLog.builder()
                .industryId(request.getIndustryId())
                .type(request.getType())
                .quantity(request.getQuantity())
                .date(request.getDate())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : EmissionStatus.PENDING)
                .build();
        return toResponse(repository.save(log));
    }

    // ─────────────────────────────────────
    // READ
    // ─────────────────────────────────────

    // Get single log by ID
    public EmissionLogDTO.Response getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    // Get all logs
    public List<EmissionLogDTO.Response> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get logs by industry
    public List<EmissionLogDTO.Response> getByIndustryId(Long industryId) {
        return repository.findByIndustryId(industryId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get logs by status
    public List<EmissionLogDTO.Response> getByStatus(EmissionStatus status) {
        return repository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get logs by type
    public List<EmissionLogDTO.Response> getByType(EmissionType type) {
        return repository.findByType(type)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get logs by date range
    public List<EmissionLogDTO.Response> getByDateRange(
            LocalDate start,
            LocalDate end) {
        return repository.findByDateBetween(start, end)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get logs by industry and status
    public List<EmissionLogDTO.Response> getByIndustryAndStatus(
            Long industryId,
            EmissionStatus status) {
        return repository.findByIndustryIdAndStatus(industryId, status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────

    // Full update
    @Transactional
    public EmissionLogDTO.Response update(
            Long id,
            EmissionLogDTO.Request request) {
        EmissionLog log = findOrThrow(id);
        log.setIndustryId(request.getIndustryId());
        log.setType(request.getType());
        log.setQuantity(request.getQuantity());
        log.setDate(request.getDate());
        if (request.getStatus() != null) {
            log.setStatus(request.getStatus());
        }
        return toResponse(repository.save(log));
    }

    // Update status only
    @Transactional
    public EmissionLogDTO.Response updateStatus(
            Long id,
            EmissionStatus status) {
        EmissionLog log = findOrThrow(id);
        log.setStatus(status);
        return toResponse(repository.save(log));
    }

    // ─────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────
    @Transactional
    public void delete(Long id) {
        findOrThrow(id);
        repository.deleteById(id);
    }

    // ─────────────────────────────────────
    // PRIVATE HELPER METHODS
    // ─────────────────────────────────────

    // Find log or throw error if not found
    private EmissionLog findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "EmissionLog not found with id: " + id));
    }

    // Convert Entity → Response DTO
    private EmissionLogDTO.Response toResponse(EmissionLog log) {
        return EmissionLogDTO.Response.builder()
                .logId(log.getLogId())
                .industryId(log.getIndustryId())
                .type(log.getType())
                .quantity(log.getQuantity())
                .date(log.getDate())
                .status(log.getStatus())
                .createdAt(log.getCreatedAt())
                .updatedAt(log.getUpdatedAt())
                .build();
    }
}