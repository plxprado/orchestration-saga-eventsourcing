package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "event_content")
public class EventContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "transaction_value", nullable = false)
    private BigDecimal transactionValue;

    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @Column(name = "aggregation_id", nullable = false)
    private UUID aggregationId;

    public Event toVo() {
        return Event.builder()
                .transactionDate(transactionDate)
                .transactionType(type)
                .transactionAmount(transactionValue)
                .externalTransactionId(transactionId)
                .build();
    }
}
