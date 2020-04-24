package com.greenarror19.springcloudsleuth.client.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    
    private final Tracer tracer;
    
    public ClientController(RestTemplate restTemplate, Tracer tracer) {
        this.restTemplate = restTemplate;
        this.tracer = tracer;
    }
    
    @GetMapping("/getData")
    public String getData(@RequestHeader("baggage-myId") String myId){
        Span currentSpan = tracer.getCurrentSpan();
        currentSpan.setBaggageItem("myId", myId);
        LOGGER.info("Getting data form client");
        LOGGER.info("Calling server 1 form client");
        String temp = restTemplate.getForObject("http://localhost:8082/server1/getData",String.class);
        LOGGER.info("Returning data from client");
        return "In-client-micro-service "+temp;
    }
}
