package com.greenarror19.springcloudsleuth.client.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.log.SleuthSlf4jProperties;
import org.springframework.cloud.sleuth.log.SpanLogger;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
    
    @Bean
    @ConditionalOnProperty(value = "spring.sleuth.log.custom.enabled", matchIfMissing = true)
    public SpanLogger customSpanLogger(SleuthSlf4jProperties sleuthSlf4jProperties) {
        return new CustomSpanLogger(sleuthSlf4jProperties.getNameSkipPattern());
    }
}
