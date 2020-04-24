package com.greenarror19.springcloudsleuth.server.configs;


import org.slf4j.MDC;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.log.Slf4jSpanLogger;

public class CustomSpanLogger extends Slf4jSpanLogger {
    
    private static final String HEADER_KEY = "baggage-myId";
    
    public CustomSpanLogger(String nameSkipPattern) {
        super(nameSkipPattern);
    }
    
    @Override
    public void logStartedSpan(Span parent, Span span) {
        super.logStartedSpan(parent, span);
        String id = span.getBaggageItem("myId");
        MDC.put(HEADER_KEY, id);
    }
    
    @Override
    public void logContinuedSpan(Span span) {
        super.logContinuedSpan(span);
        String id = span.getBaggageItem("myId");
        MDC.put(HEADER_KEY, id);
    }
}


