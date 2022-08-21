package br.com.spikelogbackintercept.adapters.in;

import br.com.spikelogbackintercept.application.ports.in.PaymentListServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentListServicePort paymentListService;

  @GetMapping()
  public String hello(@RequestHeader("correlationID") String correlation) {
    log.info("Entered list payment endpoint");
    return paymentListService.listAll();
  }
}
