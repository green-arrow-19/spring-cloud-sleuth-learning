package com.greenarror19.springcloudsleuth.server2.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.log.SleuthSlf4jProperties;
import org.springframework.cloud.sleuth.log.SpanLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author nakulgoyal
 *         23/04/20
 **/

@Configuration
public class RestTemplateConfig {
    
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
    @Bean
    @ConditionalOnProperty(value = "spring.sleuth.log.custom.enabled", matchIfMissing = true)
    public SpanLogger customSpanLogger(SleuthSlf4jProperties sleuthSlf4jProperties) {
        return new CustomSpanLogger(sleuthSlf4jProperties.getNameSkipPattern());
    }
}


