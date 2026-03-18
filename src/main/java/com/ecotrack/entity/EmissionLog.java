package com.ecotrack.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "emission_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmissionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "industry_id", nullable = false)
    private Long industryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmissionType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmissionStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = EmissionStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum EmissionType {
        @JsonProperty("CO2")
        CO2,

        @JsonProperty("NOx")
        NOx,

        @JsonProperty("SOx")
        SOx
    }

    public enum EmissionStatus {
        @JsonProperty("PENDING")
        PENDING,

        @JsonProperty("APPROVED")
        APPROVED,

        @JsonProperty("REJECTED")
        REJECTED,

        @JsonProperty("FLAGGED")
        FLAGGED
    }
}