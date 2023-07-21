package com.hanwha.drmm.config;

import com.hanwha.drmm.batch.SimpleJobRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing(dataSourceRef = "batchDs", transactionManagerRef = "batchTm")
public class BatchConfig {

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(SimpleJobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

}