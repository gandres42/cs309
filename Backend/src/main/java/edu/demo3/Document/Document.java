package edu.demo3.Document;

import java.time.ZonedDateTime;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.demo3.Club.Club;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = "club")
public class Document {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String filename;

    private String body;
    
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Club club;

    public Document(String filename, String body, Club club)
    {
        this.filename = filename;
        this.body = body;
        this.date = new Date(ZonedDateTime.now().toInstant().toEpochMilli());
        this.club = club;
    }

    public Document() {}
}

