package com.internship.tool.mapper;

import com.internship.tool.dto.ComplianceRecordDTO;
import com.internship.tool.entity.ComplianceRecord;

import java.time.ZoneOffset;

public class ComplianceRecordMapper {

    public static ComplianceRecordDTO toDTO(ComplianceRecord entity) {

        if (entity == null) {
            return null;
        }

        return ComplianceRecordDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .score(entity.getScore())
                .dueDate(entity.getDueDate() == null ? null : entity.getDueDate().atOffset(ZoneOffset.UTC))
                .createdAt(entity.getCreatedAt() == null ? null : entity.getCreatedAt().atOffset(ZoneOffset.UTC))
                .updatedAt(entity.getUpdatedAt() == null ? null : entity.getUpdatedAt().atOffset(ZoneOffset.UTC))
                .build();
    }

    public static ComplianceRecord toEntity(ComplianceRecordDTO dto) {

        if (dto == null) {
            return null;
        }

        return ComplianceRecord.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .score(dto.getScore())
                .dueDate(dto.getDueDate() == null ? null : dto.getDueDate().toLocalDateTime())
                .build();
    }
}