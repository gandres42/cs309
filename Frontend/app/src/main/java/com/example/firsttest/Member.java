package com.example.firsttest;

public class Member {

    private String name;
    private String username;
    private String email;
    private int id;
    private int photo;

    public Member(){}

    public Member(String name, String username, String email, int id, int photo) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.id = id;
        this.photo = photo;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getUsername() { return username; }

    public String getEmail() {
        return email;
    }

    public int getId() { return id; }

    public int getPhoto() { return photo; }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) { this.id = id; }

    public void setPhoto(int photo) { this.photo = photo; }
}
