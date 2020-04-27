package com.greenarror19.springcloudsleuth.client.configs;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.ExtraFieldPropagation;

/**
 * @author nakulgoyal
 *         27/04/20
 **/
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
class MyFilter extends GenericFilterBean {
    
    private final Tracer tracer;
    private final Tracing tracing;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);
    
    MyFilter(Tracer tracer, Tracing tracing) {
        this.tracer = tracer;
        this.tracing = tracing;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        LOGGER.info("In my-filter");
        HttpServletRequest request1 = (HttpServletRequest) request;
        LOGGER.info(request1.getRequestURI());
        Span currentSpan = this.tracer.currentSpan();
        if (currentSpan == null) {
            chain.doFilter(request, response);
            return;
        }
        
        // for readability we're returning trace id in a hex form
        //ExtraFieldPropagation.set(tracer.currentSpan().context(), "X-B3-TripId", "Q1234567890");
        ExtraFieldPropagation.set(tracing.currentTraceContext().get(),"X-B3-TripId", "Q1234567890");
        String requestId = ExtraFieldPropagation.get("X-B3-TripId");
    
        ((HttpServletResponse) response).addHeader("X-B3-TripId", requestId);
        MDC.put("X-B3-TripId", requestId);
        LOGGER.info("In my-filter setting trip-id");
        LOGGER.info(String.valueOf(tracing.currentTraceContext().get()));
        LOGGER.info(requestId);
        LOGGER.info(((HttpServletResponse) response).getHeader("X-B3-TripId"));
        // we can also add some custom tags
        
        currentSpan.tag("X-B3-TripId",requestId);
        chain.doFilter(request, response);
    }
}


