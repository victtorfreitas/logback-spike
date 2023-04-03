package br.com.spikelogbackintercept.application.core.usecase;

import br.com.spikelogbackintercept.application.core.modal.PaymentModel;
import br.com.spikelogbackintercept.application.ports.in.PaymentInsertUseCasePort;
import br.com.spikelogbackintercept.application.ports.out.PaymentOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentInsertUseCase implements PaymentInsertUseCasePort {

    private final PaymentOutService paymentOutService;
    @Override
    public void createPayment(PaymentModel paymentModel) {
        BigDecimal amount = paymentModel.getAmount();
        Long numberParcels = paymentModel.getNumberParcels();
        BigDecimal valueParcel = amount.divide(BigDecimal.valueOf(numberParcels), RoundingMode.HALF_UP);
        List<PaymentModel.ParcelModel> parcelModels =getParcels(paymentModel);

        paymentModel.setParcels(parcelModels);
        paymentOutService.insertPayment(paymentModel);
    }

    private List<PaymentModel.ParcelModel> getParcels(PaymentModel paymentModel) {
        List<PaymentModel.ParcelModel> parcelModels =new ArrayList<>();
        for (long i = 0; i < paymentModel.getNumberParcels(); i++) {
            PaymentModel.ParcelModel parcelModel = PaymentModel.ParcelModel.builder()
                    .price(paymentModel.getAmount())
                    .isPaid(false)
                    .datePayment(LocalDateTime.now().plusDays(30))
                    .build();
            parcelModels.add(parcelModel);
        }
        return parcelModels;
    }
}
