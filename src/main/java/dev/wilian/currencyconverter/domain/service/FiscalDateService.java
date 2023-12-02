package dev.wilian.currencyconverter.domain.service;

import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.ResponseApi;

public interface FiscalDateService {
    ResponseApi fetchExchangeRates(String country);
}
