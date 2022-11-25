package edu.demo3.DirectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
public class DirectMessageController {
    @Autowired
    private DirectMessageService directMessageService;

    @ApiOperation(value = "Get all messages from all direct messages")
    @GetMapping(path="/direct_chat/all")
    public @ResponseBody Iterable<DirectMessage> getAllMessages() 
    {
        return directMessageService.findAll();
    }
}