package br.com.spikelogbackintercept.application.core.modal;

import br.com.spikelogbackintercept.config.LogMask;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentModel {
    private BigDecimal amount;
    @Singular("parcel")
    private List<ParcelModel> parcels;
    private Long numberParcels;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ParcelModel {
        private BigDecimal price;
        private boolean isPaid;
        @LogMask
        private LocalDateTime datePayment;
    }
}
