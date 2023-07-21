package com.hanwha.drmm.batch;

import java.util.Map;
import java.util.Properties;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.InitializingBean;

public interface JobRunner extends InitializingBean {

    public JobExecution run(String jobName, Map parameters);

    public JobExecution run(String jobName, Properties parameters);

    public JobExecution rerun(String jobName, Properties parameters);

}
