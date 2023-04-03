package br.com.spikelogbackintercept.application.ports.in;

import br.com.spikelogbackintercept.application.core.modal.PaymentModel;

public interface PaymentInsertUseCasePort {
    void createPayment(PaymentModel paymentModel);
}
