package com.example.demo.executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ProjectTaskExecutor {

    private ThreadPoolTaskExecutor executor;

    public ProjectTaskExecutor() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("project_task_executor");
        executor.initialize();
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

}
