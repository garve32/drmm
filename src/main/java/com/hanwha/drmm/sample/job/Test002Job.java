package com.hanwha.drmm.sample.job;

import com.hanwha.drmm.core.batch.SimpleJobParametersIncrementer;
import com.hanwha.drmm.sample.tasklet.Test003Tasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Test002Job {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager batchTm;
    private final PlatformTransactionManager workTm;
    private final Test003Tasklet test003Tasklet;

    @Bean
    public Job test002() {
        return new JobBuilder("test002", jobRepository)
            .start(test002Step001())
            .incrementer(new SimpleJobParametersIncrementer())
            .build();
    }

    public Step test002Step001() {
        return new StepBuilder("test002Step001", jobRepository)
            .tasklet(test003Tasklet, batchTm)
            .build();
    }

}
