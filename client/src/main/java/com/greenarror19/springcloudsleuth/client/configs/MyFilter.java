package com.greenarror19.springcloudsleuth.client.configs;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.ExtraFieldPropagation;

/**
 * @author nakulgoyal
 *         27/04/20
 **/
@Component
class MyFilter extends OncePerRequestFilter {

    private final Tracer tracer;
    private final Tracing tracing;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    MyFilter(Tracer tracer, Tracing tracing) {
        this.tracer = tracer;
        this.tracing = tracing;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
                LOGGER.info("In my-filter");
                HttpServletRequest request1 = (HttpServletRequest) request;
                LOGGER.info(request1.getRequestURI());
                Span currentSpan = this.tracer.currentSpan();
                if (currentSpan == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                ExtraFieldPropagation.set(tracing.currentTraceContext().get(),"X-B3-TripId", request.getHeader("X-B3-TripId"));
                String requestId = ExtraFieldPropagation.get("X-B3-TripId");

//                ((HttpServletResponse) response).addHeader("X-B3-TripId", requestId);
//                MDC.put("X-B3-TripId", requestId);
//                MDC.put("key","7890");
                LOGGER.info("In my-filter setting trip-id");
                LOGGER.info(requestId);
                LOGGER.info(request.getHeader("X-B3-TripId"));

                currentSpan.annotate("X-B3-TripId");
                currentSpan.tag("X-B3-TripId",requestId);
        filterChain.doFilter(request,response);
    }

}


