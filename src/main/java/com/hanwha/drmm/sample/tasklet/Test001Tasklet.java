package com.hanwha.drmm.sample.tasklet;

import com.hanwha.drmm.core.batch.AbstractStep;
import java.util.Map;

import com.hanwha.drmm.sample.service.Test001Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class Test001Tasklet extends AbstractStep {

    private final Test001Service test001Service;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {
        String name = this.getClass().getSimpleName();
        log.info("#### {} start ####", name);

        Map jobParametersMap = getJobParametersMap();
        test001Service.testMethod();
        log.info("jobParametersMap = {}", jobParametersMap);
        log.info("#### {} end ####", name);

        return RepeatStatus.FINISHED;
    }
}
