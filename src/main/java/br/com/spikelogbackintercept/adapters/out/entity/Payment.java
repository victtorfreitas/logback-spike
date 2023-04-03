package br.com.spikelogbackintercept.adapters.out.entity;

import br.com.spikelogbackintercept.config.LogMask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Payment {
    @LogMask
    @MongoId
    private String objectId;
    @LogMask
    private BigDecimal amount;
    @LastModifiedDate
    private LocalDateTime creationDate;
    private List<Parcel> parcels;
    private boolean isActive;
}
