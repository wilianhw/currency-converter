package dev.wilian.currencyconverter.api.assembler;

import dev.wilian.currencyconverter.api.model.PurchaseTransactionModel;
import dev.wilian.currencyconverter.domain.model.dto.PurchaseTransaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseTransactionModelAssembler {

    private final ModelMapper modelMapper;

    public PurchaseTransactionModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PurchaseTransactionModel toModel(PurchaseTransaction purchaseTransaction) {
        return modelMapper.map(purchaseTransaction, PurchaseTransactionModel.class);
    }
}
