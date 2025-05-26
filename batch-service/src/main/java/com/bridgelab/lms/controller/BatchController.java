package com.bridgelab.lms.controller;

import com.bridgelab.lms.dto.BatchDTO;
import com.bridgelab.lms.dto.FullBatchDTO;
import com.bridgelab.lms.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(batchService.createBatch(batchDTO));
    }

    @GetMapping
    public ResponseEntity<List<BatchDTO>> getAllBatches() {
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }
    @GetMapping("/{id}/detail")
    public ResponseEntity<FullBatchDTO> getFullDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.getFullBatchDetails(id));
    }
}
