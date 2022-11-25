package com.example.firsttest;

public class Club {

    private String name;
    private int numOfMembers;
    private int id;

    public Club(){}

    public Club(String name, int numOfMembers, int id) {
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.id = id;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public int getId() { return id; }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfMembers(int numOfMembers) {
        this.numOfMembers = numOfMembers;
    }

    public void setId(int id) { this.id = id; }
}
