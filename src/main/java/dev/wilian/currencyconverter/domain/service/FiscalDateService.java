package dev.wilian.currencyconverter.domain.service;

import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.Data;

public interface FiscalDateService {
    Data exchange(String country);
}
