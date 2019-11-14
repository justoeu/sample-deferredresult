package br.com.justoeu.example.config.http.async;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@AllArgsConstructor
public class AsyncConfiguration {

    private static final String PREFIX = "AsyncTP-";

    private AsyncProperties asyncProperties;

    @Bean
    public ThreadPoolTaskExecutor asyncThreadTaskExecutor() {
        return loadExecutor();
    }

    private ThreadPoolTaskExecutor loadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncProperties.getQueueCapacity());
        executor.setThreadNamePrefix(PREFIX);
        executor.initialize();
        return executor;
    }

}