package com.hanwha.drmm.sample;

import com.hanwha.drmm.config.batch.SimpleJobParametersIncrementer;
import com.hanwha.drmm.tasklet.Test001Tasklet;
import com.hanwha.drmm.tasklet.Test002Tasklet;
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
public class MemberInsertJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager batchTm;
    private final MemberInsertTasklet memberInsertTasklet;
    private final MemberInsertTasklet_2 memberInsertTasklet_2;
    private final Test001Tasklet test001Tasklet;
    private final Test002Tasklet test002Tasklet;

    @Bean
    public Job memberUpdate() {
        return new JobBuilder("memberUpdate", jobRepository)
            .start(memberInsertStep())
            .next(memberInsertStep2())
            .incrementer(new SimpleJobParametersIncrementer())
            .build();
    }

    public Step memberInsertStep() {
        return new StepBuilder("memberInsertStep", jobRepository)
            .tasklet(memberInsertTasklet, batchTm)
            .build();
    }

    public Step memberInsertStep2() {
        return new StepBuilder("memberInsertStep2", jobRepository)
            .tasklet(memberInsertTasklet_2, batchTm)
            .build();
    }


}
