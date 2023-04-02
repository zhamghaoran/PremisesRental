package com.rental.premisesrental.service;

import org.jetbrains.annotations.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 20179
 */
@Component
@Service
public interface ScheduledTasks {
    @Scheduled
    public void databaseTask() ;
}
