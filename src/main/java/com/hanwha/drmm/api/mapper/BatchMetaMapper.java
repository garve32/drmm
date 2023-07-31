package com.hanwha.drmm.api.mapper;

import com.hanwha.drmm.api.dto.BatchJobExecutionRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchMetaMapper {

    List getBatchJobExecution(BatchJobExecutionRequest request);

}
