package com.greenarror19.springcloudsleuth.server.controllers;


import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author nakulgoyal
 *         23/04/20
 **/

@RestController
@RequestMapping("/server1")
public class ServerController {
    
    @Autowired
    RestTemplate restTemplate;
    public static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);
    
    @GetMapping("/getData")
    public String getData(){
    
        LOGGER.info("Getting data from server 1");
        LOGGER.info("Calling server 2 from server 1");
        String temp = restTemplate.getForObject("http://localhost:8083/server2/getData",String.class);
        LOGGER.info("Returning data from server 1");
        return "In-server-micro-service "+temp;
    }
}


