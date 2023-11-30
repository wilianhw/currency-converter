package dev.wilian.currencyconverter.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionModel {
    private Long id;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal purchaseAmount;
}
