package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.dto.ComplianceRecordDTO;
import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.mapper.ComplianceRecordMapper;
import com.internship.tool.service.ComplianceRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Compliance Record API",
        description = "APIs for managing compliance records"
)
@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    // ================= CREATE =================

    @Operation(summary = "Create a new compliance record")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Record created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            )
    })
    @PostMapping("/create")
public ResponseEntity<String> create(
        @Valid @RequestBody ComplianceRecordDTO dto
) {

    service.create(ComplianceRecordMapper.toEntity(dto));

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Record created successfully");
}

    // ================= GET ALL =================

    @Operation(summary = "Get all compliance records with pagination")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Records fetched successfully"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<ComplianceRecordDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Page<ComplianceRecordDTO> result =
                service.getAllPaginated(PageRequest.of(page, size))
                        .map(ComplianceRecordMapper::toDTO);

        return ResponseEntity.ok(
                ApiResponse.<Page<ComplianceRecordDTO>>builder()
                        .success(true)
                        .message("Records fetched successfully")
                        .data(result)
                        .build()
        );
    }

    // ================= GET BY ID =================

    @Operation(summary = "Get compliance record by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Record fetched successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Record not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComplianceRecordDTO>> getById(
            @PathVariable Long id
    ) {

        ComplianceRecord record = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<ComplianceRecordDTO>builder()
                        .success(true)
                        .message("Record fetched successfully")
                        .data(ComplianceRecordMapper.toDTO(record))
                        .build()
        );
    }

    // ================= UPDATE =================

    @Operation(summary = "Update compliance record")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Record updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Record not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ComplianceRecordDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ComplianceRecordDTO dto
    ) {

        ComplianceRecord updated =
                service.update(id, ComplianceRecordMapper.toEntity(dto));

        return ResponseEntity.ok(
                ApiResponse.<ComplianceRecordDTO>builder()
                        .success(true)
                        .message("Record updated successfully")
                        .data(ComplianceRecordMapper.toDTO(updated))
                        .build()
        );
    }

    // ================= DELETE =================

    @Operation(summary = "Delete compliance record")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "204",
                    description = "Record deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Record not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Record deleted successfully")
                        .data(null)
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}