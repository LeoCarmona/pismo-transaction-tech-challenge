package io.pismo.transactions.service;

import io.pismo.transactions.data.entity.Account;
import io.pismo.transactions.data.rest.account.CreateAccountRequest;
import io.pismo.transactions.exceptions.PismoException;
import io.pismo.transactions.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(final CreateAccountRequest request) {
        final Optional<Account> account = this.accountRepository.findByDocumentNumber(request.getDocumentNumber());

        if (account.isPresent()) {
            throw new PismoException(HttpStatus.BAD_REQUEST, "Account already exist!");
        }

        return this.accountRepository.save(request.toAccount());
    }

    public Account findAccountById(final Long id) {
        return this.accountRepository.findById(id)
                .orElseThrow(() -> new PismoException(HttpStatus.NOT_FOUND, "Account not found!"));
    }

    public boolean existsAccountById(final Long id) {
        return this.accountRepository.existsById(id);
    }

}
