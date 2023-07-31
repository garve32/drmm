package com.hanwha.drmm.sample.job;

import com.hanwha.drmm.core.batch.SimpleJobParametersIncrementer;
import com.hanwha.drmm.sample.tasklet.Test001Tasklet;
import com.hanwha.drmm.sample.tasklet.Test002Tasklet;
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
public class Test001Job {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager batchTm;
//    private final PlatformTransactionManager workTm;
    private final Test001Tasklet test001Tasklet;
    private final Test002Tasklet test002Tasklet;

    @Bean
    public Job test001() {
        return new JobBuilder("test001", jobRepository)
            .start(test001StepFirst())
            .next(test001StepSecond())
            .incrementer(new SimpleJobParametersIncrementer())
            .build();
    }

    public Step test001StepFirst() {
        return new StepBuilder("test001StepFirst", jobRepository)
            .tasklet(test001Tasklet, batchTm)
            .build();
    }

    public Step test001StepSecond() {
        return new StepBuilder("test001StepSecond", jobRepository)
            .tasklet(test002Tasklet, batchTm)
            .build();
    }

}
