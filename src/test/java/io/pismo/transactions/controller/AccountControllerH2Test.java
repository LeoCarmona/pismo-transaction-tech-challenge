package io.pismo.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pismo.transactions.PismoTransactionApiApplication;
import io.pismo.transactions.data.entity.Account;
import io.pismo.transactions.data.rest.account.CreateAccountRequest;
import io.pismo.transactions.repository.AccountRepository;
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
public class AccountControllerH2Test {

    private static final String ISO_TIMESTAMP_REGEX = "^\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+([+-][0-2]\\d:[0-5]\\d|Z)$";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldCreateAccount() throws Exception {
        final CreateAccountRequest request = new CreateAccountRequest();
        request.setDocumentNumber("123456789");

        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.document_number").value("123456789"));
    }

    @Test
    public void shouldNotCreateAccountWhenDocumentNumberAlreadyExists() throws Exception {
        final CreateAccountRequest request = new CreateAccountRequest();
        request.setDocumentNumber("123456789");

        accountRepository.save(Account.builder().documentNumber("123456789").build());

        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.timestamp", matchesPattern(ISO_TIMESTAMP_REGEX)))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Account already exist!"))
                .andExpect(jsonPath("$.path").value("/accounts"));
    }

    @Test
    public void shouldFindAccountWhenAccountIdExists() throws Exception {
        Account account = accountRepository.save(Account.builder().documentNumber("123456789").build());

        mvc.perform(get("/accounts/{accountId}", account.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(account.getId()))
                .andExpect(jsonPath("$.document_number").value(account.getDocumentNumber()));
    }

    @Test
    public void shouldNotFindAccountWhenAccountNotIdExists() throws Exception {
        mvc.perform(get("/accounts/{accountId}", 1))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.timestamp", matchesPattern(ISO_TIMESTAMP_REGEX)))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Account not found!"))
                .andExpect(jsonPath("$.path").value("/accounts/1"));
    }

}
