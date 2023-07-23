package com.hanwha.drmm.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.AbstractJob;

import java.util.Collection;

public class CustomJob extends AbstractJob {

    @Override
    public Step getStep(String stepName) {
        return null;
    }

    @Override
    public Collection<String> getStepNames() {
        return null;
    }

    @Override
    protected void doExecute(JobExecution execution) throws JobExecutionException {

    }
}
