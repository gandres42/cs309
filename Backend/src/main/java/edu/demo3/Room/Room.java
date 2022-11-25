package edu.demo3.Room;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.ManyToMany;

import edu.demo3.User.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = "users")
public class Room {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"rooms"})
    Set<User> users = new HashSet <User>();

    @Column(unique=true)
    private String name;

    public Room (String name)
    {
        this.name = name;
    }

    public Room() {}
}

