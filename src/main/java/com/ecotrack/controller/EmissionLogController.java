package com.ecotrack.controller;

import com.ecotrack.dto.EmissionLogDTO;
import com.ecotrack.entity.EmissionLog.EmissionStatus;
import com.ecotrack.entity.EmissionLog.EmissionType;
import com.ecotrack.service.EmissionLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emission-logs")
@RequiredArgsConstructor
@Tag(name = "Emission Logs", description = "Industry Emissions Logging APIs")
public class EmissionLogController {

    private final EmissionLogService service;

    // ─────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────

    @PostMapping
    @Operation(summary = "Log a new emission entry")
    public ResponseEntity<EmissionLogDTO.Response> create(
            @Valid @RequestBody EmissionLogDTO.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // ─────────────────────────────────────
    // READ
    // ─────────────────────────────────────

    @GetMapping
    @Operation(summary = "Get all emission logs")
    public ResponseEntity<List<EmissionLogDTO.Response>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get emission log by ID")
    public ResponseEntity<EmissionLogDTO.Response> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/industry/{industryId}")
    @Operation(summary = "Get emission logs by industry ID")
    public ResponseEntity<List<EmissionLogDTO.Response>> getByIndustry(
            @PathVariable Long industryId) {
        return ResponseEntity.ok(service.getByIndustryId(industryId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get emission logs by status")
    public ResponseEntity<List<EmissionLogDTO.Response>> getByStatus(
            @PathVariable EmissionStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get emission logs by type (CO2, NOx, SOx)")
    public ResponseEntity<List<EmissionLogDTO.Response>> getByType(
            @PathVariable EmissionType type) {
        return ResponseEntity.ok(service.getByType(type));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get emission logs between two dates")
    public ResponseEntity<List<EmissionLogDTO.Response>> getByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(service.getByDateRange(start, end));
    }

    @GetMapping("/industry/{industryId}/status/{status}")
    @Operation(summary = "Get emission logs by industry and status")
    public ResponseEntity<List<EmissionLogDTO.Response>> getByIndustryAndStatus(
            @PathVariable Long industryId,
            @PathVariable EmissionStatus status) {
        return ResponseEntity.ok(
                service.getByIndustryAndStatus(industryId, status));
    }

    // ─────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────

    @PutMapping("/{id}")
    @Operation(summary = "Update full emission log")
    public ResponseEntity<EmissionLogDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody EmissionLogDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update emission log status only")
    public ResponseEntity<EmissionLogDTO.Response> updateStatus(
            @PathVariable Long id,
            @RequestParam EmissionStatus status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    // ─────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete emission log")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}