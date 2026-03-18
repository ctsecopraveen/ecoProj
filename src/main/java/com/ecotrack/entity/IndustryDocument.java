package com.ecotrack.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "industry_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "industry_id", nullable = false)
    private Long industryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", nullable = false)
    private DocType docType;

    @Column(name = "file_uri", nullable = false, length = 500)
    private String fileUri;

    @Column(name = "uploaded_date", nullable = false)
    private LocalDate uploadedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (verificationStatus == null) verificationStatus = VerificationStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum DocType {
        PERMIT,
        COMPLIANCE
    }

    public enum VerificationStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }
}