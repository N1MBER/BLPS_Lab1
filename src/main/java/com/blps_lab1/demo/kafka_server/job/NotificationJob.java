package com.blps_lab1.demo.kafka_server.job;

import com.blps_lab1.demo.kafka_server.service.NotificationService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class NotificationJob implements Job {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        notificationService.createStats();
    }
}
