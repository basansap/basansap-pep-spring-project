package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ResponseEntity<Account>register(Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        if(username == ""||username == null || password.length() < 4) 
            return ResponseEntity.status(400).body(null);

        Account returnedAccount = accountRepository.findAccountByUsername(account.getUsername());
        if(returnedAccount != null){
            return ResponseEntity.status(409).body(null);
        }else{
            accountRepository.save(account);
            Account registeredAccount = accountRepository.findAccountByUsername(account.getUsername());
            return ResponseEntity.status(200).body(registeredAccount);
        }
    }

    // @Transactional
    public ResponseEntity<Account> login(Account account){
        Account returnedAccount = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(returnedAccount != null){
            return ResponseEntity.status(200).body(returnedAccount);
        }else{
            return ResponseEntity.status(401).body(null);
        }
    }



}
