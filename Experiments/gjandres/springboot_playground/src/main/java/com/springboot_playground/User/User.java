package com.springboot_playground.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(unique=true)
  private String email;
  
  private String password;

  private String name;

  public User (String name, String password, String email)
  {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public User() {}

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

