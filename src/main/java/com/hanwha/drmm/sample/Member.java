package com.hanwha.drmm.sample;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Member {

    private String mbrId;
    private String name;

}
