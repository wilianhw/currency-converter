package dev.wilian.currencyconverter.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionInput {
    @NotBlank(message = "Description must be not empty")
    @Length(max = 50, message = "Description must be not bigger than 50 characters")
    private String description;
    @NotNull
    private LocalDate transactionDate;
    @NotNull
    private BigDecimal purchaseAmount;
}
