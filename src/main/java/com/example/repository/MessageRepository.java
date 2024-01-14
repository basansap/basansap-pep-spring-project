package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("FROM Message WHERE posted_by = :posted_by")
    List<Message> getMessagesByPostedBy(Integer posted_by);
}
