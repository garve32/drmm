package com.hanwha.drmm.sample;

import com.hanwha.drmm.sample.src.MemberSrcMapper;
import com.hanwha.drmm.sample.tgt.MemberTgtMapper;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberInsertService {

    private final MemberSrcMapper memberSrcMapper;
    private final MemberTgtMapper memberTgtMapper;
    private final SqlSessionTemplate workSt;
    private final SqlSessionTemplate workBatchSt;

    public void insertMember() {

        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 600000; i++) {
            Member memberI = Member.builder()
                .mbrId(String.valueOf(i))
                .name("member-"+i)
                .build();
            members.add(memberI);
        }

        Instant start = Instant.now();
        for (Member member : members) {
            workBatchSt.insert("MemberTgt.insertMember", member);
        }
        workBatchSt.flushStatements();
        Instant end = Instant.now();
        long seconds = Duration.between(start, end).toSeconds();
        log.info("###배치타입 : insertMember = {} seconds", seconds);

    }

    public void insertMember2() {

        List<Member> members = new ArrayList<>();
        for (int i = 600000; i < 1200000; i++) {
            Member memberI = Member.builder()
                .mbrId(String.valueOf(i))
                .name("member-"+i)
                .build();
            members.add(memberI);
        }

        Instant start = Instant.now();
        for (Member member : members) {
            workSt.insert("MemberTgt.insertMember", member);
        }
        Instant end = Instant.now();
        long seconds = Duration.between(start, end).toSeconds();
        log.info("###일반타입 : insertMember2 = {} seconds", seconds);


    }

}
