package com.hanwha.drmm.sample;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleMapper sampleMapper;

    public List getList() {
        List list = sampleMapper.getList();
        for (Object o : list) {
            log.info("item : {}", o);
        }
        return list;
    }

}
