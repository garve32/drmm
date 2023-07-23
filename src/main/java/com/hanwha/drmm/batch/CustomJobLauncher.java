package com.hanwha.drmm.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJobLauncher extends TaskExecutorJobLauncher implements JobRunner {
    @Override
    public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        return super.run(job, jobParameters);
    }

    private final CustomJobParametersConverter jobParametersConverter;
    private final SimpleJobRegistry jobRegistry;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;

    public JobExecution run(String jobName, Map parameters) {
        return null;
    }

    public JobExecution run(String jobName, Properties parameters) {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        Job job = getJob(jobName);
        return launchJob(jobParameters, job);
    }

    private JobExecution run(String jobName, JobParameters jobParameters) {
        Job job = getJob(jobName);
        return launchJob(jobParameters, job);
    }

    public JobExecution rerun(String jobName, Properties parameters) {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        return rerun(jobName, jobParameters);
    }

    private Job getJob(String jobName) {
        try {
            return jobRegistry.getJob(jobName);
        } catch (NoSuchJobException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private JobExecution launchJob(JobParameters jobParameters, Job job) {
        try {
            return jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    private JobExecution rerun(String jobName, JobParameters jobParameters) {
        Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(jobName);
        for (JobExecution jobExecution : runningJobExecutions) {
            BatchStatus status = jobExecution.getStatus();
            log.info("jobName = {}, status = {}", jobName, status);
        }

        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if(lastJobExecution != null) {
            log.info("Last JobExecution found, jobName = {}, parameter = {}", jobName, jobParameters);
            Job job = getJob(jobName);
            JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
            if(incrementer == null) {
                throw new RuntimeException();
            }
            JobParameters incrementerNext = incrementer.getNext(jobParameters);
            return launchJob(incrementerNext, job);
        }
        return run(jobName, jobParameters);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setJobRepository(jobRepository);
    }
}
