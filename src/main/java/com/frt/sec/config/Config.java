package com.frt.sec.config;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Semaphore;

@Configuration
@EnableAsync
public class Config {

    @Bean("generatorVirtualThreadExecutor")
    public VirtualThreadTaskExecutor generatorVirtualThreadExecutor(
            @Value("${app.generator.virtual.thread.name.prefix:generator-virtual-thread-}") String threadNamePrefix,
            @Value("${app.generator.virtual.thread.max.limit:20}") int corePoolSize) {
        return generateExecutor(threadNamePrefix, corePoolSize);
    }

    private VirtualThreadTaskExecutor generateExecutor(String threadNamePrefix, int concurrencyLimit) {
        Semaphore semaphore = new Semaphore(concurrencyLimit);
        return new VirtualThreadTaskExecutor(threadNamePrefix) {
            @Override
            public void execute(@Nonnull Runnable task) {
                Runnable wrapped = () -> {
                    try {
                        semaphore.acquire();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release();
                    }
                };
                super.execute(wrapped);
            }
        };

    }
}
