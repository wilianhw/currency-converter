package dev.wilian.currencyconverter.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseTransaction {
    private Long id;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal purchaseAmount;
    private BigDecimal exchangeRate;
    private BigDecimal convertedPurchaseAmount;
}
