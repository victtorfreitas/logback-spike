package br.com.spikelogbackintercept.application.core.service;

import br.com.spikelogbackintercept.application.ports.in.PaymentListServicePort;
import lombok.extern.log4j.Log4j;

@Log4j
public class PaymentListService implements PaymentListServicePort {

  @Override
  public String listAll() {
    log.info("Entered list all payments");
    return "mock-payments";
  }
}
