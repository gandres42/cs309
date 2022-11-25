package edu.demo3.Annoucement;

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
public class Annoucement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String title;

    private String text;
    
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Club club;

    public Annoucement(String title, String text, Club club)
    {
        this.title = title;
        this.text = text;
        this.date = new Date(ZonedDateTime.now().toInstant().toEpochMilli());
        this.club = club;
    }

    public Annoucement() {}
}

