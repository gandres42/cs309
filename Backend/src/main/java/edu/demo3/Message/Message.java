package edu.demo3.Message;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.ZonedDateTime;

import edu.demo3.User.*;
import edu.demo3.Club.*;

import lombok.Data;

@Data
@Entity
@Table(name = "chat")

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int subchatid;

    private long timestamp;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club clubchat;

    public Message(User sender, Club clubchat, String message, int sub_id)
    {
        this.sender = sender;
        this.clubchat = clubchat;
        this.message = message;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.subchatid = sub_id;
    }

    public Message(){}
}
