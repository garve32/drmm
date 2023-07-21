package com.hanwha.drmm.controller;

import com.hanwha.drmm.batch.JobRunner;
import com.hanwha.drmm.batch.SimpleJobRegistry;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BatchExecutionController {

    private final JobRunner jobRunner;
    private final SimpleJobRegistry jobRegistry;

    @GetMapping
    public List<Map<String, String>> getBatchJob() {

        List<Map<String, String>> jobInfoDataset = jobRegistry.getJobInfoDataset();
        return jobInfoDataset;

    }

    @PostMapping("/exe")
    public void executeBatchJob(@RequestBody Map<String, String> param) {
        String jobName = param.get("JOB_NAME");
        String jobParam = param.get("JOB_PARAM");

        String[] paramArray = org.apache.commons.lang3.StringUtils.split(jobParam, "\n");
//        String[] param = {"test=3"};
        Properties prop = StringUtils.splitArrayElementsIntoProperties(paramArray, "=");
        log.info("prop = {}", prop);
        JobExecution jobExecution = jobRunner.rerun(jobName, prop);
        log.info("JOB ID = {}", jobExecution.getJobId());
    }

}
