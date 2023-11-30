package dev.wilian.currencyconverter.domain.service;

import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionRegisterService {

    private final TransactionRepository transactionRepository;

    public TransactionRegisterService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction register(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
