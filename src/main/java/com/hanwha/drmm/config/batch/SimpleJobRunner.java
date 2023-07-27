package com.hanwha.drmm.config.batch;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleJobRunner implements JobRunner{

    private final CustomJobParametersConverter jobParametersConverter;
    private final SimpleJobRegistry jobRegistry;
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;

    private TaskExecutor taskExecutor;

    /**
     * Set the TaskExecutor. (Optional)
     * @param taskExecutor instance of {@link TaskExecutor}.
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    // 최종 호출은 JobExecution run(Job job, JobParameters jobParameters)

    @Override
    public JobExecution run(Job job, JobParameters jobParameters) {
        try {
            return launchJob(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        }
    }

    public JobExecution run(Job job, Map parameters) {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        try {
            return launchJob(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        }
    }

    public JobExecution run(Job job, Properties parameters) {
        JobParameters jobParameters = jobParametersConverter.getJobParameters(parameters);
        try {
            return launchJob(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        }
    }

    private Job getJob(String jobName) {
        try {
            return jobRegistry.getJob(jobName);
        } catch (NoSuchJobException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * taskExecutor.execute
     */
    private JobExecution launchJob(final Job job, JobParameters jobParameters)
        throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Assert.notNull(job, "The Job must not be null.");
        Assert.notNull(jobParameters, "The JobParameters must not be null.");

        String jobName = job.getName();
        Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(jobName);
        for (JobExecution execution : runningJobExecutions) {
            BatchStatus status = execution.getStatus();
            log.info("jobName = {}, status = {}", jobName, status);
            if(status != BatchStatus.ABANDONED && status != BatchStatus.COMPLETED && status != BatchStatus.FAILED) {
                if(JobParametersUtil.equalsJobParameters(jobParameters, execution.getJobParameters())) {
                    throw new UnexpectedJobExecutionException("JobExecution already completed : " + execution);
                }
            }
        }

        final JobExecution jobExecution;
        JobExecution lastExecution = jobRepository.getLastJobExecution(job.getName(), jobParameters);
        if (lastExecution != null) {
            for (StepExecution execution : lastExecution.getStepExecutions()) {
                BatchStatus status = execution.getStatus();
                if (status.isRunning()) {
                    throw new JobExecutionAlreadyRunningException(
                        "A job execution for this job is already running: " + lastExecution);
                }
                else if (status == BatchStatus.UNKNOWN) {
                    throw new JobRestartException(
                        "Cannot restart step [" + execution.getStepName() + "] from UNKNOWN status. "
                            + "The last execution ended with a failure that could not be rolled back, "
                            + "so it may be dangerous to proceed. Manual intervention is probably necessary.");
                }
            }
            JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
            Assert.notNull(incrementer, "The incrementer must not be null.");
            jobParameters = incrementer.getNext(jobParameters);
        }

        jobExecution = jobRepository.createJobExecution(jobName, jobParameters);

        try {
            JobParameters finalJobParameters = jobParameters;
            taskExecutor.execute(() -> {
                try {
                    if (log.isInfoEnabled()) {
                        log.info("Job: [" + job + "] launched with the following parameters: [" + finalJobParameters
                            + "]");
                    }
                    job.execute(jobExecution);
                }
                catch (Throwable t) {
                    throw new IllegalStateException(t);
                }
            });
        } catch (TaskRejectedException e) {
            jobExecution.upgradeStatus(BatchStatus.FAILED);
            if (jobExecution.getExitStatus().equals(ExitStatus.UNKNOWN)) {
                jobExecution.setExitStatus(ExitStatus.FAILED.addExitDescription(e));
            }
            jobRepository.update(jobExecution);
        }

        return jobExecution;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(jobRepository != null, "A JobRepository has not been set.");
        if (taskExecutor == null) {
            taskExecutor = new SyncTaskExecutor();
        }
    }
}
