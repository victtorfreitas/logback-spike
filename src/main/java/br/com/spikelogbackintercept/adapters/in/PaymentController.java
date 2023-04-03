package br.com.spikelogbackintercept.adapters.in;

import br.com.spikelogbackintercept.adapters.in.request.PaymentRequest;
import br.com.spikelogbackintercept.application.core.modal.PaymentModel;
import br.com.spikelogbackintercept.application.core.modal.PaymentResponse;
import br.com.spikelogbackintercept.application.ports.in.PaymentInsertUseCasePort;
import br.com.spikelogbackintercept.application.ports.in.PaymentListUseCasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentListUseCasePort paymentListUseCasePort;
    private final PaymentInsertUseCasePort paymentInsertUseCasePort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponse> listAll() {
        return paymentListUseCasePort.listAllActive();
    }

    @PostMapping
    public void createPayment(@RequestBody @Validated PaymentRequest paymentRequest){
        paymentInsertUseCasePort.createPayment(getPaymentModel(paymentRequest));
    }

    private PaymentModel getPaymentModel(PaymentRequest paymentRequest) {
        return PaymentModel.builder()
                .amount(paymentRequest.getAmount())
                .numberParcels(paymentRequest.getCountParcels())
                .build();
    }
}
