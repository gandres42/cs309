package edu.demo3.Message;

import org.springframework.data.repository.CrudRepository;

import edu.demo3.Club.*;
public interface MessageRepository extends CrudRepository<Message, Integer> {
    Iterable<Message> findByClubchat(Club club);
}