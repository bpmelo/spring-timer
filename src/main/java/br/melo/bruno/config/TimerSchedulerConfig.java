package br.melo.bruno.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class TimerSchedulerConfig {

    @Value("${timer.scheduler.poolsize:10}")
    private int _SCHEDULER_POOL_SIZE;

    @Value("${timer.scheduler.name:SCHEDULER}")
    private String _SCHEDULER_NAME;

    @Bean
    @Qualifier("_TASK_SCHEDULER")
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(_SCHEDULER_POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix(_SCHEDULER_NAME);
        return threadPoolTaskScheduler;
    }
}
