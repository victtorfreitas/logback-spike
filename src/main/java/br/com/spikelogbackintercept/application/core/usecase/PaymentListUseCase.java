package br.com.spikelogbackintercept.application.core.usecase;

import br.com.spikelogbackintercept.application.core.modal.PaymentModel;
import br.com.spikelogbackintercept.application.core.modal.PaymentModel.ParcelModel;
import br.com.spikelogbackintercept.application.core.modal.PaymentResponse;
import br.com.spikelogbackintercept.application.ports.in.PaymentListUseCasePort;
import br.com.spikelogbackintercept.application.ports.out.PaymentOutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentListUseCase implements PaymentListUseCasePort {

    private final PaymentOutService paymentOutService;

    @Override
    public List<PaymentResponse> listAllActive() {
        List<PaymentResponse> collect = paymentOutService.listAll().stream()
                .map(this::getPaymentResponse)
                .collect(Collectors.toList());
        Map<String, PaymentResponse> group = paymentOutService.getGroup(collect);
        System.out.println(group);
        return collect;
    }

    private PaymentResponse getPaymentResponse(PaymentModel paymentModel) {
        final var nextAmount = getNextAmount(paymentModel.getParcels());
        final var missingParcels = countMissingParcels(paymentModel.getParcels());
        final var missingAmount = countMissingAmount(paymentModel.getParcels());
        return PaymentResponse.builder()
                .missingParcels(missingParcels)
                .missingAmount(missingAmount)
                .nextAmount(nextAmount)
                .build();
    }

    private BigDecimal countMissingAmount(List<ParcelModel> parcels) {
        return parcels.stream()
                .filter(this::isNotPaidParcel)
                .map(ParcelModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long countMissingParcels(List<ParcelModel> parcels) {
        return parcels.stream()
                .filter(this::isNotPaidParcel)
                .count();
    }

    private BigDecimal getNextAmount(List<ParcelModel> parcels) {
        return parcels.stream()
                .filter(this::isNotPaidParcel)
                .map(ParcelModel::getPrice)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }

    private boolean isNotPaidParcel(ParcelModel parcelModel) {
        return !parcelModel.isPaid();
    }
}
