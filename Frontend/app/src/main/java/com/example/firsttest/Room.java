package com.example.firsttest;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int roomId;
    private String name;
    private String users;
    private ArrayList<Integer> studentIds;
    private ArrayList<String> usernames;

    public Room(){}

    public Room(int roomId, String name, String users, ArrayList<Integer> studentIds, ArrayList<String> usernames) {
        this.roomId = roomId;
        this.name = name;
        this.users = users;
        this.studentIds = studentIds;
        this.usernames = usernames;
    }

    //getters
    public int getRoomId() { return roomId; }

    public String getName() { return name; }

    public String getUsers() { return users; }

    public ArrayList<Integer> getStudentIds() { return studentIds; }

    public ArrayList<String> getUsernames() { return usernames; }

    //setters
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public void setName(String name) { this.name = name; }

    public void setUsers(String users) { this.users = users; }

    public void setStudentIds(ArrayList<Integer> studentIds) { this.studentIds = studentIds; }

    public void setUsernames(ArrayList<String> usernames) { this.usernames = usernames; }
}
