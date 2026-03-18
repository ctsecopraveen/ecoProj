package com.ecotrack.dto;

import com.ecotrack.entity.IndustryDocument.DocType;
import com.ecotrack.entity.IndustryDocument.VerificationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IndustryDocumentDTO {

    // ─────────────────────────────────────
    // REQUEST - What we accept FROM user
    // ─────────────────────────────────────
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull(message = "Industry ID is required")
        private Long industryId;

        @NotNull(message = "Document type is required")
        private DocType docType;

        @NotBlank(message = "File URI is required")
        @Size(max = 500, message = "File URI must not exceed 500 characters")
        private String fileUri;

        @NotNull(message = "Uploaded date is required")
        private LocalDate uploadedDate;

        private VerificationStatus verificationStatus;
    }

    // ─────────────────────────────────────
    // RESPONSE - What we send BACK to user
    // ─────────────────────────────────────
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long documentId;
        private Long industryId;
        private DocType docType;
        private String fileUri;
        private LocalDate uploadedDate;
        private VerificationStatus verificationStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}