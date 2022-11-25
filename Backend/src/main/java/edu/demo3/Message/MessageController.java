package edu.demo3.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DataRetrievalFailureException;
import io.swagger.annotations.ApiOperation;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "Get all messages from all clubs")
    @GetMapping(path="/chat/all")
    public @ResponseBody Iterable<Message> getAllMessages() 
    {
        return messageService.findAll();
    }

    @ApiOperation(value = "Get messages filtered by club")
    @GetMapping(path="/chat/filter_by_club")
    public @ResponseBody Iterable<Message> getMessagesByClub(int clubid) 
    {
        return messageService.findByClubchat(clubid);
    }

    @ApiOperation(value = "Delete a message")
    @DeleteMapping(path="/chat/delete")
    public @ResponseBody Message deleteMessage(@RequestParam int id)
    {
        Message msg = messageService.findById(id);
        if (msg == null)
        {
            throw new DataRetrievalFailureException("message does not exist");
        }
        messageService.deleteById(id);
        return msg;
    }
}