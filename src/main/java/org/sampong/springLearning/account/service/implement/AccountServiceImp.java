package org.sampong.springLearning.account.service.implement;

import lombok.RequiredArgsConstructor;
import org.sampong.springLearning.account.model.Account;
import org.sampong.springLearning.account.repository.AccountRepository;
import org.sampong.springLearning.account.service.AccountService;
import org.sampong.springLearning.share.annotation.Logger;
import org.sampong.springLearning.share.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {
    private final AccountRepository repository;

    @Override
    @Logger("Find by id service")
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(repository.findByIdAndStatusTrue(id)).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "Account " + id + " not found")
        );
    }

    @Override
    public Optional<Account> findByName(String name) {
        return Optional.empty();
    }

    @Override
    @Logger("Add new account")
    public Account addNew(Account account) {
        var  newAcc = repository.existsByAccountNumberAndStatusTrue(account.getAccountNumber());
        if(newAcc){
            throw new CustomException(HttpStatus.CONFLICT, "Account already exists");
        } else {
            return Optional.of(repository.save(account)).orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot save account right now"));
        }
    }

    @Override
    @Logger("Update object")
    public Account updateObj(Account account) {
        var acc = findById(account.getId());
        if(acc.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND, "Account " + account.getId() + " not found");
        }
        var updateAcc = acc.map(account1 ->  {
            account1.setAccountNumber(account1.getAccountNumber());
            account1.setAccountHolderName(account1.getAccountHolderName());
            account1.setCurrency(account1.getCurrency());
            account1.setBalanceAmount(account1.getBalanceAmount());
            return account1;
        });
        return Optional.of(repository.save(updateAcc.get())).orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update account right now"));
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
