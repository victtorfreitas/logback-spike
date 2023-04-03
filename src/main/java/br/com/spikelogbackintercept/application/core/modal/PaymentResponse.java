package br.com.spikelogbackintercept.application.core.modal;

import br.com.spikelogbackintercept.config.LogMask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private BigDecimal nextAmount;
    private BigDecimal missingAmount;
    @LogMask
    private Long missingParcels;
}
