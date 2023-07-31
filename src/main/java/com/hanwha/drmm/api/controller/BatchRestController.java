package com.hanwha.drmm.api.controller;

import com.hanwha.drmm.api.dto.BatchJobExecutionRequest;
import com.hanwha.drmm.core.batch.SimpleJobRegistry;
import com.hanwha.drmm.core.batch.SimpleJobRunner;
import com.hanwha.drmm.api.service.BatchMetaService;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BatchRestController {

    private final SimpleJobRunner jobRunner;
    private final SimpleJobRegistry jobRegistry;

    private final BatchMetaService batchMetaService;

    @GetMapping
    public List<Map<String, String>> getBatchJob() {
        return jobRegistry.getJobInfoDataset();
    }

    @PostMapping("/exe")
    public String executeBatchJob(@RequestBody Map<String, String> param) throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        String jobName = param.get("JOB_NAME");
        String jobParam = param.get("JOB_PARAM");

        String[] paramArray = org.apache.commons.lang3.StringUtils.split(jobParam, "\n");
        Properties prop = StringUtils.splitArrayElementsIntoProperties(paramArray, "=");
        jobRunner.setTaskExecutor(new SimpleAsyncTaskExecutor());
        Job job = jobRegistry.getJob(jobName);
        JobExecution jobExecution = jobRunner.run(job, prop);
        log.info("EXECUTE JOB : {}, JOB ID : {}", jobName, jobExecution.getJobId());
        return "OK";
    }

    @GetMapping("/batchJobExecution")
    public List getBatchJobExecution(@Validated @RequestBody BatchJobExecutionRequest request) {
        return batchMetaService.getBatchJobExecution(request);
    }

}
