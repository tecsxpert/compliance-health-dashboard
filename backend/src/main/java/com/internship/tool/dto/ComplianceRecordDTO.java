package com.internship.tool.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceRecordDTO {

    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotBlank(message = "Status is required")
    private String status;
    @Min(value = 0, message = "Score must be between 0 and 100")
    @Max(value = 100, message = "Score must be between 0 and 100")
    private Integer score;
    private OffsetDateTime dueDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}