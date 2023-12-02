package dev.wilian.currencyconverter.domain.service;

import dev.wilian.currencyconverter.domain.exception.BusinessException;
import dev.wilian.currencyconverter.domain.exception.TransactionNotFoundException;
import dev.wilian.currencyconverter.domain.model.Transaction;
import dev.wilian.currencyconverter.domain.repository.TransactionRepository;
import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.DataDetails;
import dev.wilian.currencyconverter.infrastructure.service.fiscaldate.ResponseApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionRegisterServiceTest {

    public static final String BRAZIL = "Brazil";
    public static final long ID_ONE = 1L;
    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private FiscalDateService fiscalDateService;

    @Autowired
    private TransactionRegisterService transactionRegister;

    @Test
    void registerWithSuccess() {
        Transaction transaction = new Transaction();
        transaction.setDescription("Test 1");
        transaction.setTransactionDate(LocalDate.now());
        transaction.setPurchaseAmount(BigDecimal.TEN);

        assertDoesNotThrow(() -> transactionRegister.register(transaction));
    }

    @Test
    void getPurchaseTransactionWithSuccess() {
        DataDetails dataDetails = createFakeDataDetails();
        ResponseApi responseApi = new ResponseApi();
        responseApi.setData(List.of(dataDetails));

        when(transactionRepository.findById(ID_ONE)).thenReturn(Optional.of(createMockTransaction()));
        when(fiscalDateService.fetchExchangeRates(BRAZIL)).thenReturn(responseApi);

        assertDoesNotThrow(() -> transactionRegister.getPurchaseTransaction(ID_ONE, BRAZIL));
    }

    @Test
    void getPurchaseTransactionHasNoTransaction() {
        DataDetails dataDetails = createFakeDataDetails();
        ResponseApi responseApi = new ResponseApi();
        responseApi.setData(List.of(dataDetails));

        when(transactionRepository.findById(ID_ONE)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () ->
                transactionRegister.getPurchaseTransaction(ID_ONE, BRAZIL));
    }

    @Test
    void getPurchaseTransactionHasNoResultsInResponse() {
        ResponseApi responseApi = new ResponseApi();
        responseApi.setData(new ArrayList<>());

        when(transactionRepository.findById(ID_ONE)).thenReturn(Optional.of(createMockTransaction()));
        when(fiscalDateService.fetchExchangeRates(BRAZIL)).thenReturn(responseApi);

        assertThrows(BusinessException.class, () ->
                transactionRegister.getPurchaseTransaction(ID_ONE, BRAZIL));
    }

    private static DataDetails createFakeDataDetails() {
        DataDetails fakeDataDetails = new DataDetails();
        fakeDataDetails.setRecord_date("2022-01-01");
        fakeDataDetails.setCountry("FakeCountry");
        fakeDataDetails.setCurrency("FakeCurrency");
        fakeDataDetails.setCountry_currency_desc("FakeCountryCurrencyDesc");
        fakeDataDetails.setExchange_rate("1.2345");

        return fakeDataDetails;
    }

    private static Transaction createMockTransaction() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setDescription("MockTransaction");
        mockTransaction.setTransactionDate(LocalDate.of(2022, 1, 1));
        mockTransaction.setPurchaseAmount(new BigDecimal("100.00"));

        return mockTransaction;
    }
}