package com.ecotrack.repository;

import com.ecotrack.entity.IndustryDocument;
import com.ecotrack.entity.IndustryDocument.DocType;
import com.ecotrack.entity.IndustryDocument.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IndustryDocumentRepository extends JpaRepository<IndustryDocument, Long> {

    // ─────────────────────────────────────
    // Find by single field
    // ─────────────────────────────────────

    // Get all documents of a specific industry
    List<IndustryDocument> findByIndustryId(Long industryId);

    // Get all documents by type (PERMIT/COMPLIANCE)
    List<IndustryDocument> findByDocType(DocType docType);

    // Get all documents by verification status
    List<IndustryDocument> findByVerificationStatus(VerificationStatus verificationStatus);

    // ─────────────────────────────────────
    // Find by multiple fields
    // ─────────────────────────────────────

    // Get documents of specific industry by doc type
    List<IndustryDocument> findByIndustryIdAndDocType(
            Long industryId,
            DocType docType
    );

    // Get documents of specific industry by verification status
    List<IndustryDocument> findByIndustryIdAndVerificationStatus(
            Long industryId,
            VerificationStatus verificationStatus
    );

    // Get documents by doc type and verification status
    List<IndustryDocument> findByDocTypeAndVerificationStatus(
            DocType docType,
            VerificationStatus verificationStatus
    );
}