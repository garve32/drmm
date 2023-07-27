package com.hanwha.drmm.config.batch;

import java.util.List;
import java.util.Map;
import org.springframework.batch.core.configuration.JobRegistry;

public interface SimpleJobRegistry extends JobRegistry {

    public List<Map<String, String>> getJobInfoDataset();

}
