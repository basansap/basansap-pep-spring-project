package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    Account findAccountByUsername(String username);
    Account findAccountByUsernameAndPassword(String username, String password);
}
