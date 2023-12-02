package dev.wilian.currencyconverter.api.controller;

import dev.wilian.currencyconverter.api.assembler.PurchaseTransactionModelAssembler;
import dev.wilian.currencyconverter.api.assembler.TransactionModelAssembler;
import dev.wilian.currencyconverter.api.disassembler.TransactionInputDisassembler;
import dev.wilian.currencyconverter.api.model.PurchaseTransactionModel;
import dev.wilian.currencyconverter.api.model.TransactionModel;
import dev.wilian.currencyconverter.api.model.input.TransactionInput;
import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.service.TransactionRegisterService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionModelAssembler transactionModelAssembler;
    private final TransactionInputDisassembler transactionInputDisassembler;
    private final TransactionRegisterService transactionRegister;
    private final PurchaseTransactionModelAssembler purchaseTransactionModelAssembler;

    public TransactionController(TransactionModelAssembler transactionModelAssembler, TransactionInputDisassembler transactionInputDisassembler, TransactionRegisterService transactionRegister, PurchaseTransactionModelAssembler purchaseTransactionModelAssembler) {
        this.transactionModelAssembler = transactionModelAssembler;
        this.transactionInputDisassembler = transactionInputDisassembler;
        this.transactionRegister = transactionRegister;
        this.purchaseTransactionModelAssembler = purchaseTransactionModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionModel create(@Valid @RequestBody TransactionInput transactionInput) {
        Transaction transaction = transactionInputDisassembler.toObjectDomain(transactionInput);

        transaction = transactionRegister.register(transaction);

        return transactionModelAssembler.toModel(transaction);
    }

    @GetMapping("/{transactionId}")
    public PurchaseTransactionModel currency(
            @PathVariable Long transactionId,
            @PathParam("country") String country
    ) {
        return purchaseTransactionModelAssembler.toModel(transactionRegister.getPurchaseTransaction(transactionId, country));
    }
}
