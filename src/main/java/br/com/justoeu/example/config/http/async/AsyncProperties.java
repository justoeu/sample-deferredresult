package br.com.justoeu.example.config.http.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.thread.async")
@Data
public class AsyncProperties {

  private Integer corePoolSize = 1;
  private Integer maxPoolSize = 5;
  private Integer queueCapacity = 10;
}