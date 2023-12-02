package dev.wilian.currencyconverter.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyExchangeModel extends TransactionModel{
    private BigDecimal exchangeRate;
    private BigDecimal convertedPurchaseAmount;
}
