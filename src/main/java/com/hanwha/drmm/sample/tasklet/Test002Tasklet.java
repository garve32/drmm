package com.hanwha.drmm.sample.tasklet;

import com.hanwha.drmm.core.batch.AbstractStep;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
public class Test002Tasklet extends AbstractStep implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {
        String name = this.getClass().getSimpleName();
        log.info("#### {} start ####", name);

        Map jobParametersMap = getJobParametersMap();
        log.info("jobParametersMap = {}", jobParametersMap);
        log.info("#### {} end ####", name);

        return RepeatStatus.FINISHED;
    }
}
