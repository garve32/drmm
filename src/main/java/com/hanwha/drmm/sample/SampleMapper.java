package com.hanwha.drmm.sample;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapper {
    List getList();
}
