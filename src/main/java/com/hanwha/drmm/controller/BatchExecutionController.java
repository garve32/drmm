package com.hanwha.drmm.controller;

import com.hanwha.drmm.batch.CustomJobLauncher;
import com.hanwha.drmm.batch.CustomJobParametersConverter;
import com.hanwha.drmm.batch.JobRunner;
import com.hanwha.drmm.batch.SimpleJobRegistry;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BatchExecutionController {

    private final CustomJobLauncher jobRunner;
    private final JobLauncher jobLauncher;
    private final SimpleJobRegistry jobRegistry;
    private final JobRepository jobRepository;
    private final CustomJobParametersConverter jobParametersConverter;

    @GetMapping
    public List<Map<String, String>> getBatchJob() {

        List<Map<String, String>> jobInfoDataset = jobRegistry.getJobInfoDataset();
        return jobInfoDataset;

    }

    @PostMapping("/exe")
    public void executeBatchJob(@RequestBody Map<String, String> param) throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        String jobName = param.get("JOB_NAME");
        String jobParam = param.get("JOB_PARAM");

        String[] paramArray = org.apache.commons.lang3.StringUtils.split(jobParam, "\n");
//        String[] param = {"test=3"};
        Properties prop = StringUtils.splitArrayElementsIntoProperties(paramArray, "=");
        log.info("prop = {}", prop);
        jobRunner.setTaskExecutor(new SimpleAsyncTaskExecutor());
        Job job1 = jobRegistry.getJob(jobName);
        JobParameters jobParameters = jobParametersConverter.getJobParameters(prop);
        JobExecution jobExecution = jobLauncher.run(job1, jobParameters);
//        JobExecution jobExecution = jobRunner.rerun(jobName, prop);
        log.info("JOB ID = {}", jobExecution.getJobId());
    }

}
