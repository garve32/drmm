package com.hanwha.drmm.sample.tgt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberTgtMapper {
    void insertMember();
    void updateMember();
}
