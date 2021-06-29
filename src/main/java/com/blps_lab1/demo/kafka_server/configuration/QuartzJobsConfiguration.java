package com.blps_lab1.demo.kafka_server.configuration;

import com.blps_lab1.demo.kafka_server.job.NotificationJob;
import com.blps_lab1.demo.main_server.beans.Notification;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
@Profile("stats")
public class QuartzJobsConfiguration {
    private static final String CRON_EVERY_FIVE_MINUTES = "0/5 0 * ? * * *";

    @Bean(name = "notificationStats")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(NotificationJob.class, "Member Statistics Job");
    }
//
    @Bean(name = "notificationStatsTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("notificationStats") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, 30000, "Notififcation Statistics Trigger");
    }
//
    @Bean(name = "memberClassStatsTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("notificationStats") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "Class Statistics Trigger");
    }
}
