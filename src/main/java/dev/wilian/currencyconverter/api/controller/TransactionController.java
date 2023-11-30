package dev.wilian.currencyconverter.api.controller;

import dev.wilian.currencyconverter.api.assembler.TransactionModelAssembler;
import dev.wilian.currencyconverter.api.disassembler.TransactionInputDisassembler;
import dev.wilian.currencyconverter.api.model.TransactionModel;
import dev.wilian.currencyconverter.api.model.input.TransactionInput;
import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.service.TransactionRegisterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionModelAssembler transactionModelAssembler;
    private final TransactionInputDisassembler transactionInputDisassembler;
    private final TransactionRegisterService transactionRegister;

    public TransactionController(TransactionModelAssembler transactionModelAssembler, TransactionInputDisassembler transactionInputDisassembler, TransactionRegisterService transactionRegister) {
        this.transactionModelAssembler = transactionModelAssembler;
        this.transactionInputDisassembler = transactionInputDisassembler;
        this.transactionRegister = transactionRegister;
    }

    @PostMapping
    public TransactionModel create(@Valid @RequestBody TransactionInput transactionInput) {
        Transaction transaction = transactionInputDisassembler.toObjectDomain(transactionInput);

        transaction = transactionRegister.register(transaction);

        return transactionModelAssembler.toModel(transaction);
    }
}
