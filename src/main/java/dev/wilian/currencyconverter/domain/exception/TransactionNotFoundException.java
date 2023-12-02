package dev.wilian.currencyconverter.domain.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(Long transactionId) {
        this(String.format("Not exist transaction of code %d", transactionId));
    }
}
