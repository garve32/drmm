package com.hanwha.drmm.sample.src;

import com.hanwha.drmm.sample.Member;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberSrcMapper {
    List<Member> getList();
}
