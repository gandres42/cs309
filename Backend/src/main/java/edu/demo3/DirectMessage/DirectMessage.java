package edu.demo3.DirectMessage;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import edu.demo3.User.*;
import edu.demo3.Room.Room;
import lombok.Data;

@Data
@Entity
@Table(name = "direct_chat")

public class DirectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long timestamp;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public DirectMessage(User sender, Room room, String message)
    {
        this.sender = sender;
        this.room = room;
        this.message = message;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
    }

    public DirectMessage(){}
}
