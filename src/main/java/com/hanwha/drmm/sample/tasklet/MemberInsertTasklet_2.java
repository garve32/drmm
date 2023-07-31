package com.hanwha.drmm.sample.tasklet;

import com.hanwha.drmm.core.batch.AbstractStep;
import com.hanwha.drmm.sample.service.MemberInsertService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class MemberInsertTasklet_2 extends AbstractStep {

    private final MemberInsertService memberInsertService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {
        String name = this.getClass().getSimpleName();
        log.info("#### {} start ####", name);

        Map jobParametersMap = getJobParametersMap();
        memberInsertService.insertMember2();
        log.info("#### {} end ####", name);

        return RepeatStatus.FINISHED;
    }
}
