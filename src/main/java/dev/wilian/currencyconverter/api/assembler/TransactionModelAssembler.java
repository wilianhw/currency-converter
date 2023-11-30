package dev.wilian.currencyconverter.api.assembler;

import dev.wilian.currencyconverter.api.model.TransactionModel;
import dev.wilian.currencyconverter.domain.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionModelAssembler {

    private final ModelMapper modelMapper;

    public TransactionModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionModel toModel(Transaction transaction) {
        return modelMapper.map(transaction, TransactionModel.class);
    }
}
