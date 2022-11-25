package com.example.firsttest;

import org.json.JSONObject;
import java.util.Date;

public class Announcement {

    private String header;
    private String text;
    private int day;
    private int month;
    private int year;
    private int clubId;

    public Announcement(){}

    public Announcement(String header, String text, int day, int month, int year, int clubId) {
        this.header = header;
        this.text = text;
        this.day = day;
        this.month = month;
        this.year = year;
        this.clubId = clubId;
    }

    //getters
    public String getHeader() { return header; }

    public String getText() { return text; }

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public int getClubId() { return clubId; }

    //setters
    public void setHeader(String header) { this.header = header; }

    public void setText(String text) { this.text = text; }

    public void setDay(int day) { this.day = day; }

    public void setMonth(int month) { this.month = month; }

    public void setYear(int year) { this.year = year; }

    public void setClubId(int clubId) { this.clubId = clubId; }
}
