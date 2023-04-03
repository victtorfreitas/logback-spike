package br.com.spikelogbackintercept.adapters.out.service;

import br.com.spikelogbackintercept.adapters.out.entity.Parcel;
import br.com.spikelogbackintercept.adapters.out.entity.Payment;
import br.com.spikelogbackintercept.adapters.out.repository.PaymentRepository;
import br.com.spikelogbackintercept.application.core.modal.PaymentModel;
import br.com.spikelogbackintercept.application.core.modal.PaymentModel.ParcelModel;
import br.com.spikelogbackintercept.application.core.modal.PaymentResponse;
import br.com.spikelogbackintercept.application.ports.out.PaymentOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentOutServiceImpl implements PaymentOutService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentModel> listAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::getPaymentModel)
                .collect(Collectors.toList());
    }

    @Override
    public void insertPayment(PaymentModel paymentModel) {
        paymentRepository.save(getPaymetnEntity(paymentModel));
    }

    @Override
    public Map<String, PaymentResponse> getGroup(List<PaymentResponse> collect) {
        Map<String, PaymentResponse> paymentResponseMap = new HashMap<>();
        paymentResponseMap.put("First", collect.stream().findFirst().orElse(null));
        paymentResponseMap.put("Another", collect.stream().findAny().orElse(null));
        return paymentResponseMap;
    }

    private Payment getPaymetnEntity(PaymentModel paymentModel) {
        return Payment.builder()
                .isActive(true)
                .amount(paymentModel.getAmount())
                .parcels(getParcels(paymentModel.getParcels()))
                .build();
    }

    private List<Parcel> getParcels(List<ParcelModel> parcels) {
        return parcels.stream().map(this::getParcel).collect(Collectors.toList());
    }

    private PaymentModel getPaymentModel(Payment payment) {
        return PaymentModel.builder()
                .amount(payment.getAmount())
                .parcels(getParcels(payment))
                .build();
    }

    private List<ParcelModel> getParcels(Payment payment) {
        return payment.getParcels()
                .stream().map(this::getParcel)
                .collect(Collectors.toList());
    }

    private ParcelModel getParcel(Parcel parcel) {
        return ParcelModel.builder()
                .isPaid(parcel.isPaid())
                .price(parcel.getPrice())
                .build();
    }

    private Parcel getParcel(ParcelModel parcel) {
        return Parcel.builder()
                .datePayment(parcel.getDatePayment())
                .price(parcel.getPrice())
                .isPaid(parcel.isPaid())
                .build();
    }
}
