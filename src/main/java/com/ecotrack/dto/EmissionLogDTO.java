package com.ecotrack.dto;

import com.ecotrack.entity.EmissionLog.EmissionStatus;
import com.ecotrack.entity.EmissionLog.EmissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmissionLogDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Request body for creating emission log")
    public static class Request {

        @NotNull(message = "Industry ID is required")
        @Schema(example = "1")
        private Long industryId;

        @NotNull(message = "Emission type is required")
        @Schema(example = "CO2", allowableValues = {"CO2", "NOx", "SOx"})
        private EmissionType type;

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
        @Schema(example = "250.50")
        private BigDecimal quantity;

        @NotNull(message = "Date is required")
        @Schema(example = "2026-03-13")
        private LocalDate date;

        @Schema(example = "PENDING", allowableValues = {"PENDING", "APPROVED", "REJECTED", "FLAGGED"})
        private EmissionStatus status;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long logId;
        private Long industryId;
        private EmissionType type;
        private BigDecimal quantity;
        private LocalDate date;
        private EmissionStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}