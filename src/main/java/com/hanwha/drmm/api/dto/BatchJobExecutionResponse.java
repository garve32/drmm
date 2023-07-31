package com.hanwha.drmm.api.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BatchJobExecutionResponse {

    private String jobName;
    private Long jobExecutionId;
    private Long version;
    private Long jobInstanceId;
    private LocalDateTime createTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String exitCode;
    private String exitMessage;
    private LocalDateTime lastUpdated;
    private String params;

}
