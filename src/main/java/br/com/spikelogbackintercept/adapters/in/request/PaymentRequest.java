package br.com.spikelogbackintercept.adapters.in.request;

import br.com.spikelogbackintercept.config.LogMask;
import lombok.Data;

import java.math.BigDecimal;

@Data
@LogMask
public class PaymentRequest {
    @LogMask
    private BigDecimal amount;
    private Long countParcels;
}
