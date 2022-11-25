package edu.demo3.User;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.demo3.Club.*;
import edu.demo3.Permissions.UserPermission;
import edu.demo3.Room.Room;

@Data
@Entity
@EqualsAndHashCode(exclude = "clubs")
public class User {

    @Id
    @Column(nullable = false)
    private int studentid;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_clubs") 
    @JsonIgnoreProperties("users")
    Set<Club> clubs = new HashSet<Club>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rooms") 
    @JsonIgnoreProperties("users")
    Set<Room> rooms = new HashSet<Room>();

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String settings;

    private UserPermission permissionlevel;

    public User (int studentid, String username, String firstname, String lastname, String email, String password)
    {
        this.studentid = studentid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;

        //users have default permission of NEW_STUDENT
        this.permissionlevel = UserPermission.NEW_STUDENT;
    }

    public User(){}
}