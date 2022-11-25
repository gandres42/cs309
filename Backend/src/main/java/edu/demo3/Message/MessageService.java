package edu.demo3.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.demo3.Club.ClubRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private ClubRepository clubRepo;

    public Iterable<Message> findAll()
    {
        return messageRepo.findAll();
    }

    public Iterable<Message> findByClubchat(int clubid)
    {
        return messageRepo.findByClubchat(clubRepo.findById(clubid));
    }

    public Message findById(int messageid)
    {
        return messageRepo.findById(messageid).get();
    }

    public void deleteById(int messageid)
    {
        messageRepo.deleteById(messageid);
    }
}
