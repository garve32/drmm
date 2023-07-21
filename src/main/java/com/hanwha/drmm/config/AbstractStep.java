package com.hanwha.drmm.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@Slf4j
public class AbstractStep implements StepExecutionListener {

    private StepExecution stepExecution;

    public Map getJobParametersMap() {
        Map<String, JobParameter<?>> jobParameterMap = stepExecution.getJobParameters().getParameters();
        for (Entry<String, JobParameter<?>> stringJobParameterEntry : jobParameterMap.entrySet()) {
            log.info("key = {}, value = {}", stringJobParameterEntry.getKey(), stringJobParameterEntry.getValue());
        }
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
}
