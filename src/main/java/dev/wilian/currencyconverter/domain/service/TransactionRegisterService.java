package dev.wilian.currencyconverter.domain.service;

import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.model.dto.CurrencyConvert;
import dev.wilian.currencyconverter.domain.repository.TransactionRepository;
import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.DataDetails;
import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.ResponseApi;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionRegisterService {

    private final TransactionRepository transactionRepository;
    private final FiscalDateService fiscalDateService;

    public TransactionRegisterService(TransactionRepository transactionRepository, FiscalDateService fiscalDateService) {
        this.transactionRepository = transactionRepository;
        this.fiscalDateService = fiscalDateService;
    }

    public Transaction register(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public CurrencyConvert exchange(Long transactionId, String countryCurrency) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Not exist transaction of codigo %d", transactionId)
                ));

        ResponseApi responseApi = fiscalDateService.exchange(countryCurrency);
        Optional<DataDetails> optionalDataDetails = responseApi.getData().stream().findFirst();
        if (optionalDataDetails.isEmpty())
            throw new RuntimeException("Não foi encontrado dados para a moeda selecionada");

        return getCurrencyConvert(optionalDataDetails.get(), transaction);
    }

    private static CurrencyConvert getCurrencyConvert(DataDetails dataDetails, Transaction transaction) {
        CurrencyConvert currencyConvert = new CurrencyConvert();
        currencyConvert.setId(transaction.getId());
        currencyConvert.setDescription(transaction.getDescription());
        currencyConvert.setTransactionDate(transaction.getTransactionDate());
        currencyConvert.setPurchaseAmount(transaction.getPurchaseAmount());
        BigDecimal exchangeRate = new BigDecimal(dataDetails.getExchange_rate());
        currencyConvert.setExchangeRate(exchangeRate);
        currencyConvert.setConvertedPurchaseAmount(exchangeRate.multiply(transaction.getPurchaseAmount()));
        return currencyConvert;
    }
}
