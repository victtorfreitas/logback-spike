package br.com.spikelogbackintercept.adapters.out.entity;

import br.com.spikelogbackintercept.config.LogMask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Parcel {
    private Long number;
    private String description;
    private BigDecimal price;
    private LocalDateTime datePayment;
    private boolean isPaid;
}
