package com.hanwha.drmm.sample.service;

import com.hanwha.drmm.sample.Member;
import com.hanwha.drmm.sample.src.MemberSrcMapper;
import com.hanwha.drmm.sample.tgt.MemberTgtMapper;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberInsertService {

    private final MemberSrcMapper memberSrcMapper;
    private final MemberTgtMapper memberTgtMapper;
    private final SqlSession workSt;
    private final SqlSession workBatchSt;

    @Transactional(value = "workTm")
    public void insertMember() {

        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 65535; i++) {
            Member memberI = Member.builder()
                .mbrId("M-1-"+i)
                .name("member-"+i)
                .build();
            members.add(memberI);
        }

        Instant start = Instant.now();
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
//            if(i == 22222) {
//                Member build = Member.builder()
//                    .mbrId("MEMMMMMMM-1-" + i)
//                    .name("member-" + i)
//                    .build();
//                workBatchSt.insert("MemberTgt.insertMember", build);
//            } else {
//                workBatchSt.insert("MemberTgt.insertMember", member);
//            }
            workBatchSt.insert("MemberTgt.insertMember", member);
//            if(i % 10000 == 0) {
//                workBatchSt.flushStatements();
//            }

        }
//        for (Member member : members) {
//            workBatchSt.insert("MemberTgt.insertMember", member);
//        }
//        workBatchSt.flushStatements();
        Instant end = Instant.now();
        long seconds = Duration.between(start, end).toSeconds();
        log.info("###배치타입 : insertMember = {} seconds", seconds);

    }

    public void insertMember2() {

        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 65535; i++) {
            Member memberI = Member.builder()
                .mbrId("M-2-"+i)
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
