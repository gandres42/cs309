package edu.demo3.DirectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectMessageService {
    @Autowired
    private DirectMessageRepository directMessageRepo;

    public Iterable<DirectMessage> findAll()
    {
        return directMessageRepo.findAll();
    }
}
