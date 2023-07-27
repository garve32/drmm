package com.hanwha.drmm.config.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.stereotype.Component;

@Component
public class SimpleJobRegistryImpl extends MapJobRegistry implements SimpleJobRegistry {

    public List<Map<String, String>> getJobInfoDataset() {
        return getBatchInfoDataset();
    }

    private List<Map<String, String>> getBatchInfoDataset() {
        List<Map<String, String>> list = new ArrayList<>();
        for (String jobName : this.getJobNames()) {
            Map<String, String> row = new HashMap<>();
            Job job;
            try {
                job = getJob(jobName);
            } catch (NoSuchJobException e) {
                throw new RuntimeException(e);
            }
            row.put("JOB_NAME", jobName);
            row.put("JOB_CLASS", job.getClass().getName());
            row.put("JOB_PARAMETERS_INCREMENTER", String.valueOf(job.getJobParametersIncrementer()));
            row.put("JOB_PARAMETERS_VALIDATOR", String.valueOf(job.getJobParametersValidator()));
            list.add(row);
        }
        return list;
    }

}
