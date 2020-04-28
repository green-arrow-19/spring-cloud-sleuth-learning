package com.greenarror19.springcloudsleuth.client.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient webClient;
    @Autowired
    private Tracer tracer;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @GetMapping("/getData")
    public Mono<String> getData(){
        LOGGER.info("Getting data form client {}",tracer.currentSpan().context());
        LOGGER.info("Calling server 1 form client");

        return webClient.get()
                 .uri("http://localhost:8082/server1/getData")
                 .exchange()
                 .doOnSuccess(clientResponse -> {
                     LOGGER.info("Got response from service2 [{}]", clientResponse);
                     LOGGER.info("Service1: Baggage for [key] is [" + ExtraFieldPropagation.get("key") + "]");
                 })
                 .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
    }
}
