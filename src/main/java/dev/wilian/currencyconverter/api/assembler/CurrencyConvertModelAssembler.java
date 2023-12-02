package dev.wilian.currencyconverter.api.assembler;

import dev.wilian.currencyconverter.api.model.CurrencyExchangeModel;
import dev.wilian.currencyconverter.domain.model.dto.CurrencyConvert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConvertModelAssembler {

    private final ModelMapper modelMapper;

    public CurrencyConvertModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CurrencyExchangeModel toModel(CurrencyConvert currencyConvert) {
        return modelMapper.map(currencyConvert, CurrencyExchangeModel.class);
    }
}
