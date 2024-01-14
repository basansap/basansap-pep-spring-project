package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    // #1. POST localhost:8080/register
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        return accountService.register(account);
    }

    // #2. POST localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<Account>login(@RequestBody Account account){
        return accountService.login(account); 
    }

    //#3. POST localhost:8080/messages
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessages(@RequestBody Message message){
        return messageService.createMessage(message);
    }

    //#4. GET localhost:8080/messages.
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        return messageService.getMessages();
    }

    //#5. GET localhost:8080/messages/{message_id}.
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        return messageService.getMessageById(message_id);
    }

    //#6. DELETE localhost:8080/messages/{message_id}.
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id){
        return messageService.deleteMessageById(message_id);
    }

    //#7. PATCH localhost:8080/messages/{message_id}
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message){
        return messageService.updateMessageById(message_id, message);
    }

    //#8. GET localhost:8080/accounts/{account_id}/messages
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer account_id){
        return messageService.getMessagesByAccountId(account_id);
    }
}
