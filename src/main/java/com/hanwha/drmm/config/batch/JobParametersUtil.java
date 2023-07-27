package com.hanwha.drmm.config.batch;

import com.hanwha.drmm.config.batch.SimpleJobParametersIncrementer;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

@Slf4j
public class JobParametersUtil {

    public static boolean equalsJobParameters(final JobParameters jobParameters1, final JobParameters jobParameters2) {
        if(log.isDebugEnabled()) {
            log.debug("jobParam1:{}, jobParam2:{}", jobParameters1, jobParameters2);
            if(jobParameters1 != null) {
                log.debug("jobParam1.getParameters():{}", jobParameters1.getParameters());
            }
            if(jobParameters2 != null) {
                log.debug("jobParam2.getParameters():{}", jobParameters2.getParameters());
            }
        }

        if(jobParameters1 == null || jobParameters2 == null) {
            return false;
        }

        Map<String, JobParameter<?>> paramMap1 = jobParameters1.getParameters();
        Map<String, JobParameter<?>> paramMap2 = jobParameters2.getParameters();

        //1.size 로 비교
        int size1 = paramMap1.size();
        if(paramMap1.containsKey(SimpleJobParametersIncrementer.BATCH_RUN_TOKEN)) {
            size1 = size1 -1;
        }
        int size2 = paramMap2.size();
        if(paramMap2.containsKey(SimpleJobParametersIncrementer.BATCH_RUN_TOKEN)) {
            size2 = size2 -1;
        }
        if(size1 != size2) {
            return false;
        }

        //2.value 로 비교
        for(String key : jobParameters1.getParameters().keySet()) {
            //SimpleJobParametersIncrementer 의 batch 토큰은 무시
            if(!SimpleJobParametersIncrementer.BATCH_RUN_TOKEN.equals(key)) {
                if(!StringUtils.equals(jobParameters1.getString(key), jobParameters2.getString(key))) {
                    return false;
                }
            }
        }

        return true;
    }

}
