package br.com.spikelogbackintercept.application.ports.out;

import br.com.spikelogbackintercept.application.core.modal.PaymentModel;
import br.com.spikelogbackintercept.application.core.modal.PaymentResponse;

import java.util.List;
import java.util.Map;

public interface PaymentOutService {
    List<PaymentModel> listAll();
    void insertPayment(PaymentModel paymentModel);

    Map<String, PaymentResponse> getGroup(List<PaymentResponse> collect);
}
