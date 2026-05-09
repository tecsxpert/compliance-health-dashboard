package com.internship.tool.service;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.repository.ComplianceRecordRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplianceRecordService {

    private final ComplianceRecordRepository repository;

    // ✅ CREATE
    @CacheEvict(value = "compliance", allEntries = true)
    public ComplianceRecord create(ComplianceRecord record) {
        return repository.save(record);
    }

    // ✅ GET BY ID
    @Cacheable(value = "compliance")
    public ComplianceRecord getById(Long id) {
        System.out.println("Fetching from DB...");
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    // ✅ GET ALL (LIST)
    @Cacheable(value = "compliance")
    public List<ComplianceRecord> getAll() {
        System.out.println("Fetching all from DB...");
        return repository.findAll();
    }

    // ✅ GET ALL PAGINATED
    @Cacheable(value = "compliance")
    public Page<ComplianceRecord> getAllPaginated(PageRequest pageRequest) {
        System.out.println("Fetching paginated from DB...");
        return repository.findAll(pageRequest);
    }

    // ✅ UPDATE
    @CacheEvict(value = "compliance", allEntries = true)
    public ComplianceRecord update(Long id, ComplianceRecord record) {

        ComplianceRecord existing = getById(id);

        existing.setTitle(record.getTitle());
        existing.setDescription(record.getDescription());
        existing.setStatus(record.getStatus());
        existing.setScore(record.getScore());
        existing.setDueDate(record.getDueDate());

        return repository.save(existing);
    }

    // ✅ DELETE
    @CacheEvict(value = "compliance", allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // ✅ FILTER BY STATUS
    @Cacheable(value = "compliance")
    public List<ComplianceRecord> filterByStatus(String status) {
        System.out.println("Filtering by status from DB...");
        return repository.findByStatus(status);
    }

    // ✅ FILTER BY DATE RANGE
    @Cacheable(value = "compliance")
    public List<ComplianceRecord> filterByDateRange(LocalDateTime start, LocalDateTime end) {
        System.out.println("Filtering by date from DB...");
        return repository.findByDueDateBetween(start, end);
    }

    // ✅ SEARCH
    @Cacheable(value = "compliance")
    public List<ComplianceRecord> search(String keyword) {
        System.out.println("Searching from DB...");
        return repository.findByTitleContainingIgnoreCase(keyword);
    }
}