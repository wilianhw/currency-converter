package dev.wilian.currencyconverter.api.disassembler;

import dev.wilian.currencyconverter.api.model.input.TransactionInput;
import dev.wilian.currencyconverter.domain.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionInputDisassembler {
    private final ModelMapper modelMapper;

    public TransactionInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transaction toObjectDomain(TransactionInput transactionInput) {
        return modelMapper.map(transactionInput, Transaction.class);
    }
}
