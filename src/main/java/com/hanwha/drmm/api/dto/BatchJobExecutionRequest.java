package com.hanwha.drmm.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BatchJobExecutionRequest {

    private String jobName;
    private String status;
    @NotBlank
    private String fromDt;
    @NotBlank
    private String endDt;

}
