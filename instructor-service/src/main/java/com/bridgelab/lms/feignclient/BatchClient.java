package com.bridgelab.lms.feignclient;


import com.bridgelab.lms.dto.BatchDTO;
import com.bridgelab.lms.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "batch-service", path = "/api/batches") // service name registered with Eureka or your config
public interface BatchClient {



    @GetMapping("/{id}/batches")
    public ResponseEntity<List<BatchDTO>> getAllBatchOfInstructor(@PathVariable Long instructorId) ;



}
