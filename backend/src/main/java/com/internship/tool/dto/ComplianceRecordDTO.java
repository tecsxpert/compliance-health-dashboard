package com.internship.tool.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceRecordDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Integer score;
    private OffsetDateTime dueDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}