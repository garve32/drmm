package com.hanwha.drmm.controller;

import com.hanwha.drmm.batch.SimpleJobRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchJobService {

    private final SimpleJobRegistry jobRegistry;

}
