package com.example.firsttest;

public class User {

    private int studentId;
    private String username;

    public User(){}

    public User(int studentId, String username) {
        this.studentId = studentId;
        this.username = username;
    }

    //getters
    public int getStudentId() { return studentId; }

    public String getUsername() { return username; }

    //setters
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public void setUsername(String username) { this.username = username; }
}
