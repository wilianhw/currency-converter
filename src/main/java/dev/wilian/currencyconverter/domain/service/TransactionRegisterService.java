package dev.wilian.currencyconverter.domain.service;

import dev.wilian.currencyconverter.domain.exception.BusinessException;
import dev.wilian.currencyconverter.domain.exception.TransactionNotFoundException;
import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.model.dto.PurchaseTransaction;
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

    public PurchaseTransaction getPurchaseTransaction(Long transactionId, String countryCurrency) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        ResponseApi responseApi = fiscalDateService.fetchExchangeRates(countryCurrency);
        if (responseApi.getData().isEmpty())
            throw new BusinessException("The purchase cannot be converted to the target currency.");
        Optional<DataDetails> optionalDataDetails = responseApi.getData().stream().findFirst();

        return getCurrencyConvert(optionalDataDetails.get(), transaction);
    }

    private static PurchaseTransaction getCurrencyConvert(DataDetails dataDetails, Transaction transaction) {
        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
        purchaseTransaction.setId(transaction.getId());
        purchaseTransaction.setDescription(transaction.getDescription());
        purchaseTransaction.setTransactionDate(transaction.getTransactionDate());
        purchaseTransaction.setPurchaseAmount(transaction.getPurchaseAmount());
        BigDecimal exchangeRate = new BigDecimal(dataDetails.getExchange_rate());
        purchaseTransaction.setExchangeRate(exchangeRate);
        purchaseTransaction.setConvertedPurchaseAmount(exchangeRate.multiply(transaction.getPurchaseAmount()));
        return purchaseTransaction;
    }
}
