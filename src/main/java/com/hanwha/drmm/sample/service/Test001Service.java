package com.hanwha.drmm.sample.service;

import org.springframework.stereotype.Service;

@Service
public class Test001Service {

    public void testMethod() {
        try {
            Thread.sleep(3000); // 3초 딜레이
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
