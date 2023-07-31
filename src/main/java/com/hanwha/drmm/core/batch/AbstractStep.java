package com.hanwha.drmm.core.batch;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class AbstractStep implements StepExecutionListener, Tasklet, InitializingBean {

    private StepExecution stepExecution;

    public Map getJobParametersMap() {
        Map<String, JobParameter<?>> jobParameterMap = stepExecution.getJobParameters().getParameters();
        Map<String, Object> resultMap = new HashMap<>();
        for (Entry<String, JobParameter<?>> entry : jobParameterMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue().getValue());
        }
        return resultMap;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String name = this.getClass().getSimpleName();
        log.info("#### {} start ####", name);

        Map jobParametersMap = getJobParametersMap();
        log.info("jobParametersMap = {}", jobParametersMap);
        log.info("#### {} end ####", name);

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
