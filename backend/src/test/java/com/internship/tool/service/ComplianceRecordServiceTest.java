package com.internship.tool.service;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.ComplianceRecordRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplianceRecordServiceTest {

    @Mock
    private ComplianceRecordRepository repository;

    @InjectMocks
    private ComplianceRecordServiceImpl service;

    // 🔧 Helper method
    private ComplianceRecord sample() {
        ComplianceRecord r = new ComplianceRecord();
        r.setId(1L);
        r.setTitle("Test");
        r.setStatus("OPEN");
        r.setDueDate(LocalDateTime.now());
        return r;
    }

    // ✅ 1. CREATE
    @Test
    void testCreateSuccess() {
        ComplianceRecord record = sample();

        when(repository.save(any(ComplianceRecord.class))).thenReturn(record);

        ComplianceRecord result = service.create(record);

        assertNotNull(result);
        assertEquals("Test", result.getTitle());
        verify(repository, times(1)).save(record);
    }

    // ✅ 2. GET BY ID (SUCCESS)
    @Test
    void testGetByIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(sample()));

        ComplianceRecord result = service.getById(1L);

        assertEquals(1L, result.getId());
    }

    // ✅ 3. GET BY ID (NOT FOUND)
    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getById(1L));
    }

    // ✅ 4. GET ALL
    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(sample()));

        List<ComplianceRecord> result = service.getAll();

        assertEquals(1, result.size());
    }

    // ✅ 5. PAGINATION
    @Test
    void testGetAllPaginated() {
        Page<ComplianceRecord> page = new PageImpl<>(List.of(sample()));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ComplianceRecord> result =
                service.getAllPaginated(PageRequest.of(0, 5));

        assertEquals(1, result.getContent().size());
    }

    // ✅ 6. UPDATE SUCCESS
    @Test
    void testUpdateSuccess() {
        ComplianceRecord existing = sample();
        ComplianceRecord updated = sample();
        updated.setTitle("Updated");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(ComplianceRecord.class))).thenReturn(updated);

        ComplianceRecord result = service.update(1L, updated);

        assertEquals("Updated", result.getTitle());
    }

    // ✅ 7. UPDATE NOT FOUND
    @Test
    void testUpdateNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(1L, sample()));
    }

    // ✅ 8. DELETE
   @Test
   void testDelete() {
       ComplianceRecord record = new ComplianceRecord();
       record.setId(1L);
   
       when(repository.findById(1L))
               .thenReturn(Optional.of(record));
   
       service.delete(1L);
   
       verify(repository, times(1)).delete(record);
   }

    // ✅ 9. FILTER BY STATUS
    @Test
    void testFilterByStatus() {
        when(repository.findByStatus("OPEN")).thenReturn(List.of(sample()));

        List<ComplianceRecord> result = service.filterByStatus("OPEN");

        assertEquals(1, result.size());
    }

    // ✅ 10. SEARCH
   @Test
   void testSearch() {
   
       ComplianceRecord record = new ComplianceRecord();
       record.setId(1L);
       record.setTitle("Test Record");
   
       List<ComplianceRecord> records = List.of(record);
   
       when(repository.search(anyString()))
               .thenReturn(records);
   
       List<ComplianceRecord> result = service.search("Test");
   
       assertNotNull(result);
       assertEquals(1, result.size());
       assertEquals("Test Record", result.get(0).getTitle());
   
       verify(repository, times(1)).search(anyString());
   }
}