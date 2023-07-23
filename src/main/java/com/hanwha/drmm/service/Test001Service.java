package com.hanwha.drmm.service;

import org.springframework.stereotype.Service;

@Service
public class Test001Service {

    public String testMethod() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }

}
