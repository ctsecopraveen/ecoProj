package com.ecotrack.controller;

import com.ecotrack.dto.IndustryDocumentDTO;
import com.ecotrack.entity.IndustryDocument.DocType;
import com.ecotrack.entity.IndustryDocument.VerificationStatus;
import com.ecotrack.service.IndustryDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/industry-documents")
@RequiredArgsConstructor
@Tag(name = "Industry Documents", description = "Industry Compliance Document APIs")
public class IndustryDocumentController {

    private final IndustryDocumentService service;

    // ─────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────

    @PostMapping
    @Operation(summary = "Submit a new compliance document")
    public ResponseEntity<IndustryDocumentDTO.Response> create(
            @Valid @RequestBody IndustryDocumentDTO.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // ─────────────────────────────────────
    // READ
    // ─────────────────────────────────────

    @GetMapping
    @Operation(summary = "Get all industry documents")
    public ResponseEntity<List<IndustryDocumentDTO.Response>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get document by ID")
    public ResponseEntity<IndustryDocumentDTO.Response> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/industry/{industryId}")
    @Operation(summary = "Get documents by industry ID")
    public ResponseEntity<List<IndustryDocumentDTO.Response>> getByIndustry(
            @PathVariable Long industryId) {
        return ResponseEntity.ok(service.getByIndustryId(industryId));
    }

    @GetMapping("/type/{docType}")
    @Operation(summary = "Get documents by type (PERMIT or COMPLIANCE)")
    public ResponseEntity<List<IndustryDocumentDTO.Response>> getByDocType(
            @PathVariable DocType docType) {
        return ResponseEntity.ok(service.getByDocType(docType));
    }

    @GetMapping("/verification-status/{status}")
    @Operation(summary = "Get documents by verification status")
    public ResponseEntity<List<IndustryDocumentDTO.Response>> getByVerificationStatus(
            @PathVariable VerificationStatus status) {
        return ResponseEntity.ok(service.getByVerificationStatus(status));
    }

    @GetMapping("/industry/{industryId}/type/{docType}")
    @Operation(summary = "Get documents by industry and doc type")
    public ResponseEntity<List<IndustryDocumentDTO.Response>> getByIndustryAndDocType(
            @PathVariable Long industryId,
            @PathVariable DocType docType) {
        return ResponseEntity.ok(
                service.getByIndustryAndDocType(industryId, docType));
    }

    @GetMapping("/industry/{industryId}/verification-status/{status}")
    @Operation(summary = "Get documents by industry and verification status")
    public ResponseEntity<List<IndustryDocumentDTO.Response>> getByIndustryAndVerificationStatus(
            @PathVariable Long industryId,
            @PathVariable VerificationStatus status) {
        return ResponseEntity.ok(
                service.getByIndustryAndVerificationStatus(industryId, status));
    }

    // ─────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────

    @PutMapping("/{id}")
    @Operation(summary = "Update full document")
    public ResponseEntity<IndustryDocumentDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody IndustryDocumentDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/verify")
    @Operation(summary = "Update document verification status only")
    public ResponseEntity<IndustryDocumentDTO.Response> updateVerificationStatus(
            @PathVariable Long id,
            @RequestParam VerificationStatus status) {
        return ResponseEntity.ok(
                service.updateVerificationStatus(id, status));
    }

    // ─────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}