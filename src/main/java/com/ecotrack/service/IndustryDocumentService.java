package com.ecotrack.service;

import com.ecotrack.dto.IndustryDocumentDTO;
import com.ecotrack.entity.IndustryDocument;
import com.ecotrack.entity.IndustryDocument.DocType;
import com.ecotrack.entity.IndustryDocument.VerificationStatus;
import com.ecotrack.repository.IndustryDocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndustryDocumentService {

    private final IndustryDocumentRepository repository;

    // ─────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────
    @Transactional
    public IndustryDocumentDTO.Response create(
            IndustryDocumentDTO.Request request) {
        IndustryDocument doc = IndustryDocument.builder()
                .industryId(request.getIndustryId())
                .docType(request.getDocType())
                .fileUri(request.getFileUri())
                .uploadedDate(request.getUploadedDate())
                .verificationStatus(request.getVerificationStatus() != null
                        ? request.getVerificationStatus()
                        : VerificationStatus.PENDING)
                .build();
        return toResponse(repository.save(doc));
    }

    // ─────────────────────────────────────
    // READ
    // ─────────────────────────────────────

    // Get single document by ID
    public IndustryDocumentDTO.Response getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    // Get all documents
    public List<IndustryDocumentDTO.Response> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get documents by industry
    public List<IndustryDocumentDTO.Response> getByIndustryId(
            Long industryId) {
        return repository.findByIndustryId(industryId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get documents by doc type
    public List<IndustryDocumentDTO.Response> getByDocType(DocType docType) {
        return repository.findByDocType(docType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get documents by verification status
    public List<IndustryDocumentDTO.Response> getByVerificationStatus(
            VerificationStatus status) {
        return repository.findByVerificationStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get documents by industry and doc type
    public List<IndustryDocumentDTO.Response> getByIndustryAndDocType(
            Long industryId,
            DocType docType) {
        return repository.findByIndustryIdAndDocType(industryId, docType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get documents by industry and verification status
    public List<IndustryDocumentDTO.Response> getByIndustryAndVerificationStatus(
            Long industryId,
            VerificationStatus status) {
        return repository.findByIndustryIdAndVerificationStatus(industryId, status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────

    // Full update
    @Transactional
    public IndustryDocumentDTO.Response update(
            Long id,
            IndustryDocumentDTO.Request request) {
        IndustryDocument doc = findOrThrow(id);
        doc.setIndustryId(request.getIndustryId());
        doc.setDocType(request.getDocType());
        doc.setFileUri(request.getFileUri());
        doc.setUploadedDate(request.getUploadedDate());
        if (request.getVerificationStatus() != null) {
            doc.setVerificationStatus(request.getVerificationStatus());
        }
        return toResponse(repository.save(doc));
    }

    // Update verification status only
    @Transactional
    public IndustryDocumentDTO.Response updateVerificationStatus(
            Long id,
            VerificationStatus status) {
        IndustryDocument doc = findOrThrow(id);
        doc.setVerificationStatus(status);
        return toResponse(repository.save(doc));
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

    // Find document or throw error if not found
    private IndustryDocument findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "IndustryDocument not found with id: " + id));
    }

    // Convert Entity → Response DTO
    private IndustryDocumentDTO.Response toResponse(IndustryDocument doc) {
        return IndustryDocumentDTO.Response.builder()
                .documentId(doc.getDocumentId())
                .industryId(doc.getIndustryId())
                .docType(doc.getDocType())
                .fileUri(doc.getFileUri())
                .uploadedDate(doc.getUploadedDate())
                .verificationStatus(doc.getVerificationStatus())
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}