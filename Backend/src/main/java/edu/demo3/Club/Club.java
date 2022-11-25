package edu.demo3.Club;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import edu.demo3.Annoucement.Annoucement;
import edu.demo3.Category.Category;
import edu.demo3.Meeting.Meeting;
import edu.demo3.User.User;
import edu.demo3.Document.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = "users")
public class Club {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "clubs", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"clubs"})
    Set<User> users = new HashSet <User>();

    @ManyToMany(mappedBy = "clubs", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"clubs"})
    Set<Category> categories = new HashSet <Category>();




    @Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    Set<Annoucement> annoucements = new HashSet<Annoucement>();

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    Set<Meeting> meetings = new HashSet<Meeting>();

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    Set<Document> documents = new HashSet<Document>();

    private HashMap<Integer, String> subchats;

    private String description;
    private String settings;

    public Club (String name, String description)
    {
        this.name = name;
        this.description = description;
        this.subchats = new HashMap<Integer, String>();
    }

    public Club() {}
}

