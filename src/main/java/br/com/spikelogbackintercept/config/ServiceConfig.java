package br.com.spikelogbackintercept.config;

import br.com.spikelogbackintercept.application.core.service.PaymentListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  @Bean
  public PaymentListService paymentListService() {
    return new PaymentListService();
  }
}
