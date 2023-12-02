package dev.wilian.currencyconverter.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class Transaction {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDate transactionDate;

    private BigDecimal purchaseAmount;

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        if (purchaseAmount != null)
            purchaseAmount = purchaseAmount.setScale(2, RoundingMode.HALF_UP);
        this.purchaseAmount = purchaseAmount;
    }
}
