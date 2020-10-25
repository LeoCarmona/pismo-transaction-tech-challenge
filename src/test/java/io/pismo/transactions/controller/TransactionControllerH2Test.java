package io.pismo.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pismo.transactions.PismoTransactionApiApplication;
import io.pismo.transactions.data.entity.Account;
import io.pismo.transactions.data.entity.OperationType;
import io.pismo.transactions.data.enums.OperationTypeEnum;
import io.pismo.transactions.data.rest.account.CreateAccountRequest;
import io.pismo.transactions.data.rest.transaction.TransactionRequest;
import io.pismo.transactions.repository.AccountRepository;
import io.pismo.transactions.repository.OperationTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = PismoTransactionApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class TransactionControllerH2Test {

    private static final String ISO_TIMESTAMP_REGEX = "^\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+([+-][0-2]\\d:[0-5]\\d|Z)$";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    private Account account;

    @Before
    public void setup() {
        account = this.createAccount();
    }

    @Test
    public void shouldNotCreateTransactionWithoutAnAccount() throws Exception {
        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(0L);
        request.setOperationTypeId(0L);
        request.setAmount(new BigDecimal("50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.timestamp", matchesPattern(ISO_TIMESTAMP_REGEX)))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Account not found!"))
                .andExpect(jsonPath("$.path").value("/transactions"));
    }

    @Test
    public void shouldNotCreateTransactionWithoutValidOperationType() throws Exception {
        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(0L);
        request.setAmount(new BigDecimal("50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.timestamp", matchesPattern(ISO_TIMESTAMP_REGEX)))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Operation Type not found!"))
                .andExpect(jsonPath("$.path").value("/transactions"));
    }

    @Test
    public void shouldCreateCashTransactionWithPositiveAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.CASH.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("CASH"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("-50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreateCashTransactionWithNegativeAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.CASH.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("-50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("CASH"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("-50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreateInstallmentTransactionWithPositiveAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.INSTALLMENT.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("INSTALLMENT"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("-50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreateInstallmentTransactionWithNegativeAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.INSTALLMENT.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("-50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("INSTALLMENT"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("-50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreateWithdrawTransactionWithPositiveAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.WITHDRAW.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("WITHDRAW"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("-50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreateWithdrawTransactionWithNegativeAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.WITHDRAW.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("-50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("WITHDRAW"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("-50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreatePaymentTransactionWithPositiveAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.PAYMENT.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("PAYMENT"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    @Test
    public void shouldCreatePaymentTransactionWithNegativeAmount() throws Exception {
        final OperationType operationType = operationTypeRepository.findOperationTypeByMnemonic(OperationTypeEnum.PAYMENT.name()).get();

        final TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setAmount(new BigDecimal("-50"));

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.mnemonic").value("PAYMENT"))
                .andExpect(jsonPath("$.description").value(operationType.getDescription()))
                .andExpect(jsonPath("$.amount").value(new BigDecimal("50.0")))
                .andExpect(jsonPath("$.event_date", matchesPattern(ISO_TIMESTAMP_REGEX)));
    }

    private Account createAccount() {
        final Account account = new Account();
        account.setDocumentNumber("123456789");

        return accountRepository.save(account);
    }

}
