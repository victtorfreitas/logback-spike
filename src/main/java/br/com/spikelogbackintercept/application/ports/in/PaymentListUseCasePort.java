package br.com.spikelogbackintercept.application.ports.in;

import br.com.spikelogbackintercept.application.core.modal.PaymentResponse;

import java.util.List;
import java.util.Map;

public interface PaymentListUseCasePort {

    List<PaymentResponse> listAllActive();

}
