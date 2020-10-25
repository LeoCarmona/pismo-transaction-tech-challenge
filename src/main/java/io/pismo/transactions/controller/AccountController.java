package io.pismo.transactions.controller;

import io.pismo.transactions.aspect.LogExecutionTime;
import io.pismo.transactions.data.entity.Account;
import io.pismo.transactions.data.rest.account.AccountResponse;
import io.pismo.transactions.data.rest.account.CreateAccountRequest;
import io.pismo.transactions.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@RestController
@RequestMapping("/accounts")
@Api(value = "Accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Create an account")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @LogExecutionTime
    public AccountResponse createAccount(@Valid @RequestBody final CreateAccountRequest request) {
        final Account account = this.accountService.createAccount(request);

        return new AccountResponse(account);
    }

    @ApiOperation(value = "Find an account by id")
    @GetMapping(path = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    public AccountResponse findAccountById(@PathVariable final Long accountId) {
        final Account account = this.accountService.findAccountById(accountId);

        return new AccountResponse(account);
    }

}
