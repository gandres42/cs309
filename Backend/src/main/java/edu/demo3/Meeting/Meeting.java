package edu.demo3.Meeting;

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
public class Meeting {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String location;

    private String title;
    
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Club club;

    public Meeting(String location, String title, Club club, int year, int month, int day)
    {
        this.location = location;
        this.title = title;
        this.club = club;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        this.date = cal.getTime();
    }

    public Meeting() {}
}

