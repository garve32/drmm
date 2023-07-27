package com.hanwha.drmm.config.batch;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

@Slf4j
public class SimpleJobParametersIncrementer implements JobParametersIncrementer {

    public final static String BATCH_RUN_TOKEN = "batch.run.token";

    @Override
    public JobParameters getNext(JobParameters parameters) {
        String prefix = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String seq = RandomStringUtils.randomAlphanumeric(10);
        String s = prefix + seq;
        log.info("s={}", s);
        if(parameters == null || parameters.isEmpty()) {
            return new JobParametersBuilder().addString(BATCH_RUN_TOKEN, s).toJobParameters();
        }
        return new JobParametersBuilder(parameters).addString(BATCH_RUN_TOKEN, s).toJobParameters();
    }
}
