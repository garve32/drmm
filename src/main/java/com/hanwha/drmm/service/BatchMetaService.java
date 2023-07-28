package com.hanwha.drmm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<Map<String, String>> getBatchJobExecution() {
        List<Map<String, String>> result = new ArrayList<>();

        return result;
    }

}
