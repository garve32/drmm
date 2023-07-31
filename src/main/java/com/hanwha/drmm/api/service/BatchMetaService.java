package com.hanwha.drmm.api.service;

import com.hanwha.drmm.api.dto.BatchJobExecutionRequest;
import com.hanwha.drmm.api.mapper.BatchMetaMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchMetaService {

    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;
    private final BatchMetaMapper batchMetaMapper;

    public List getBatchJobExecution(BatchJobExecutionRequest request) {

        return batchMetaMapper.getBatchJobExecution(request);

    }

}
