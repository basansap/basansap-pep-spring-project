package com.example.service;

import java.util.Optional;
import java.util.List;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    // Constructor which accept messageRepository & accountRepository
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // Handle create new message based on the message object will automatically assign message_id
    @Transactional
    public ResponseEntity<Message> createMessage(Message message){
        //The creation of the message will be successful 
        //if and only if the message_text is not blank,
        // is not over 255 characters, and posted_by refers to a real, existing user

        String message_text = message.getMessage_text();
        Integer posted_by = message.getPosted_by();

        Optional<Account> postedAccount = accountRepository.findById(posted_by);
        if(message_text.isEmpty() || message_text == null || message_text.length() > 255 || postedAccount.isEmpty()){
            return ResponseEntity.status(400).body(null);
        }
        // if message_text passed the above condition and posted_by account id is valid 
        // then we can save the message to the database
        Message savedMessage = messageRepository.save(message);
        
        // if for some reason the new message is not created we have to throw the 400 status
        if(savedMessage != null){
            return ResponseEntity.status(200).body(savedMessage);
        }
        return ResponseEntity.status(400).body(null);
    }

    // Handle retrieve all the message in the message table function
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.status(200).body(messages);
    }

    // Handle retrieve message by message_id function 
    public ResponseEntity<Message> getMessageById(int message_id){
        Optional<Message> returnedMessage = messageRepository.findById(message_id);
        if(returnedMessage.isEmpty()){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(returnedMessage.get());
    }

    // Handle delete message by message_id function 
    public ResponseEntity<Integer> deleteMessageById(int message_id){
        Optional<Message> foundMessage = messageRepository.findById(message_id);
        if(foundMessage.isPresent()){
            messageRepository.delete(foundMessage.get());
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }
    // Handle the patch message by message_id function

    @Transactional
    public ResponseEntity<Integer> updateMessageById(int message_id, Message message){
        String new_message_text = message.getMessage_text();
        //check the new_message_text if it is in the critaria
        if(new_message_text.isEmpty() || new_message_text == null || new_message_text.length() > 255){
                return ResponseEntity.status(400).body(null);
        }

        // if the new message passed the condition
        Optional<Message> foundMessage = messageRepository.findById(message_id);
        if(foundMessage.isPresent()){
            Message getMessage = foundMessage.get();
            getMessage.setMessage_text(new_message_text);
            messageRepository.save(getMessage);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<List<Message>> getMessagesByAccountId(Integer posted_by){
        List<Message> messages = messageRepository.getMessagesByPostedBy(posted_by);
        if(messages != null){
            return ResponseEntity.status(200).body(messages);
        }
        return ResponseEntity.status(200).body(null);
        
    }
}
