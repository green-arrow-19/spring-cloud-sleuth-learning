package com.greenarror19.springcloudsleuth.server2.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/server2")
public class ServerController {
    
    @Autowired
    RestTemplate restTemplate;
    
    public static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);
    
    @GetMapping("/getData")
    public String getData(){
        LOGGER.info("Getting data from server 2");
        String data = "In-server2-micro-service";
        LOGGER.info("Returning data from server 2");
        return data;
    }
}


