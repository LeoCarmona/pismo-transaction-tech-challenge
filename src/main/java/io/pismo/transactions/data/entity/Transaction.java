package io.pismo.transactions.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Entity
@Table(name = "TRANSACTION")
@Data
@EqualsAndHashCode(of = "id")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OPERATION_TYPE_ID")
    private OperationType operationType;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "EVENT_DATE", nullable = false)
    private Instant eventDate;

}
