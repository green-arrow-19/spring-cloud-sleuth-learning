package com.greenarror19.springcloudsleuth.server2.configs;

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
}


