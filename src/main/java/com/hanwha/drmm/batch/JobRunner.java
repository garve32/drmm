package com.hanwha.drmm.batch;

import java.util.Map;
import java.util.Properties;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.InitializingBean;

public interface JobRunner extends InitializingBean {

    public JobExecution run(Job job, JobParameters jobParameters);

    public JobExecution run(Job job, Map map);

    public JobExecution run(Job job, Properties properties);


}
