package br.com.spikelogbackintercept.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class MdcInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    MDC.put("CorrelationId", request.getHeader("correlationID"));
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    MDC.remove("CorrelationId");
  }
}
