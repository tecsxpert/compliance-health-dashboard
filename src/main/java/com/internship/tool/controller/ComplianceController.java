package com.internship.tool.controller;

import com.internship.tool.entity.Compliance;
import com.internship.tool.service.ComplianceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    private final ComplianceService service;

    public ComplianceController(ComplianceService service) {
        this.service = service;
    }

    // ✅ VIEW ALL (ALL ROLES)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','VIEWER')")
    public ResponseEntity<List<Compliance>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ✅ CREATE (ADMIN + MANAGER)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Compliance> create(@RequestBody Compliance compliance) {
        return ResponseEntity.ok(service.save(compliance));
    }

    // ✅ DELETE (ONLY ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ STATUS FILTER (ALL ROLES)  ❗ YOU MISSED THIS
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','VIEWER')")
    public List<Compliance> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    // ✅ DATE FILTER (ALL ROLES) ❗ YOU MISSED THIS
    @GetMapping("/date")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','VIEWER')")
    public List<Compliance> getByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        return service.getByDateRange(start, end);
    }

    // ✅ SEARCH (ALL ROLES)
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','VIEWER')")
    public ResponseEntity<List<Compliance>> search(@RequestParam("q") String q) {
        return ResponseEntity.ok(service.search(q));
    }

    // ✅ STATS (ADMIN + MANAGER)
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(service.getStats());
    }

    // ✅ UPDATE (ADMIN + MANAGER)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Compliance> update(@PathVariable Long id,
                                            @RequestBody Compliance compliance) {
        return ResponseEntity.ok(service.update(id, compliance));
    }
}