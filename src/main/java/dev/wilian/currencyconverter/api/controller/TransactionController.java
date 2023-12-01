package dev.wilian.currencyconverter.api.controller;

import dev.wilian.currencyconverter.api.assembler.TransactionModelAssembler;
import dev.wilian.currencyconverter.api.disassembler.TransactionInputDisassembler;
import dev.wilian.currencyconverter.api.model.TransactionModel;
import dev.wilian.currencyconverter.api.model.input.TransactionInput;
import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.service.FiscalDateService;
import dev.wilian.currencyconverter.domain.service.TransactionRegisterService;
import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.Data;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionModelAssembler transactionModelAssembler;
    private final TransactionInputDisassembler transactionInputDisassembler;
    private final TransactionRegisterService transactionRegister;
    private final FiscalDateService fiscalDateService;

    public TransactionController(TransactionModelAssembler transactionModelAssembler, TransactionInputDisassembler transactionInputDisassembler, TransactionRegisterService transactionRegister, FiscalDateService fiscalDateService) {
        this.transactionModelAssembler = transactionModelAssembler;
        this.transactionInputDisassembler = transactionInputDisassembler;
        this.transactionRegister = transactionRegister;
        this.fiscalDateService = fiscalDateService;
    }

    @PostMapping
    public TransactionModel create(@Valid @RequestBody TransactionInput transactionInput) {
        Transaction transaction = transactionInputDisassembler.toObjectDomain(transactionInput);

        transaction = transactionRegister.register(transaction);

        return transactionModelAssembler.toModel(transaction);
    }

    @GetMapping
    public Data currency(@PathParam("country") String country) {
        return fiscalDateService.exchange(country);
    }
}
