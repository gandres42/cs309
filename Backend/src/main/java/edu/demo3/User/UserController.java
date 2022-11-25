package edu.demo3.User;

import edu.demo3.Club.ClubRepository;
import edu.demo3.Permissions.UserPermission;
import edu.demo3.Room.Room;
import edu.demo3.Room.RoomRepository;
import io.swagger.annotations.ApiOperation;
import edu.demo3.Club.Club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController{
	@Autowired
    private UserService userService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired RoomRepository roomRepository;

    @ApiOperation(value = "Create a new student user")
    @PutMapping(path="/users/create")
    public @ResponseBody User createUser (@RequestParam int studentid, @RequestParam String username, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String password) {
        if (userService.findByStudentid(studentid) != null)
        {
            throw new DataIntegrityViolationException("studentid already exists");
        }
        return userService.save(new User(studentid, username, firstname, lastname, email, password));
    }

    @ApiOperation(value = "Attempt a user login")
    @PostMapping(path="/users/login")
    public @ResponseBody User loginUser (@RequestParam int studentid, @RequestParam String password) {
        User user = userService.findByStudentid(studentid);
        if (user == null || !password.equals(user.getPassword()))
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        return user;
    }

    @ApiOperation(value = "Update a user's username")
    @PostMapping(path = "/users/update_username")
    public @ResponseBody User updateName(@RequestParam int studentid, @RequestParam String value) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        // User actor = userService.findByStudentid(actorId);
        // if (actor == null)
        // {
        //     throw new DataRetrievalFailureException("actor does not exist");
        // }
        // if (actorId != studentid)
        // {
        //     throw new DataRetrievalFailureException("user does not have permission to update this information, only users can update their data");
        // }

        user.setUsername(value);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Update a user's firstnme")
    @PostMapping(path = "/users/update_firstname")
    public @ResponseBody User updateFirstname(@RequestParam int studentid, @RequestParam String value) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        // User actor = userService.findByStudentid(actorId);
        // if (actor == null)
        // {
        //     throw new DataRetrievalFailureException("actor does not exist");
        // }
        // if (actorId != studentid)
        // {
        //     throw new DataRetrievalFailureException("user does not have permission to update this information, only users can update their data");
        // }
        user.setFirstname(value);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Update a user's lastname")
    @PostMapping(path = "/users/update_lastname")
    public @ResponseBody User updateLastname(@RequestParam int studentid, @RequestParam String value) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        // User actor = userService.findByStudentid(actorId);
        // if (actor == null)
        // {
        //     throw new DataRetrievalFailureException("actor does not exist");
        // }
        // if (actorId != studentid)
        // {
        //     throw new DataRetrievalFailureException("user does not have permission to update this information, only users can update their data");
        // }
        user.setLastname(value);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Update a user's email")
    @PostMapping(path = "/users/update_email")
    public @ResponseBody User updateEmail(@RequestParam int studentid, @RequestParam String value) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        // User actor = userService.findByStudentid(actorId);
        // if (actor == null)
        // {
        //     throw new DataRetrievalFailureException("actor does not exist");
        // }
        // if (actorId != studentid)
        // {
        //     throw new DataRetrievalFailureException("user does not have permission to update this information, only users can update their data");
        // }
        user.setEmail(value);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Update a user's password")
    @PostMapping(path = "/users/update_password")
    public @ResponseBody User updatePassword(@RequestParam int studentid, @RequestParam String current_password, @RequestParam String value) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        if (!user.getPassword().equals(current_password))
        {
            throw new DataRetrievalFailureException("password does not match");
        }
        // User actor = userService.findByStudentid(actorId);
        // if (actor == null)
        // {
        //     throw new DataRetrievalFailureException("actor does not exist");
        // }
        // if (actorId != studentid)
        // {
        //     throw new DataRetrievalFailureException("user does not have permission to update this information, only users can update their data");
        // }
        user.setPassword(value);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Update a user's settings config")
    @PostMapping(path = "/users/update_settings")
    public @ResponseBody User updateSettings(@RequestParam int studentid, @RequestParam String value) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        // User actor = userService.findByStudentid(actorId);
        // if (actor == null)
        // {
        //     throw new DataRetrievalFailureException("actor does not exist");
        // }
        // if (actorId != studentid)
        // {
        //     throw new DataRetrievalFailureException("user does not have permission to update this information, only users can update their data");
        // }
        user.setSettings(value);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Get all users")
    @GetMapping(path="/users/all")
    public @ResponseBody Iterable<User> getAllUsers() 
    {
        return userService.findAll();
    }

    @ApiOperation(value = "Find a user by student id")
    @GetMapping(path="/users/search_student_id")
    public @ResponseBody User getUserByStudentid(@RequestParam int studentid) 
    {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            System.out.println(user);
            throw new DataRetrievalFailureException("user does not exist");
        }
        return user;
    }

    @ApiOperation(value = "Delete a user with studentid, password confirmation, id of user performing the account deletion")
    @DeleteMapping(path="/users/delete")
    public @ResponseBody Optional<User> deleteUser(@RequestParam int studentid, @RequestParam String password, @RequestParam int actorId)
    {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        User actor = userService.findByStudentid(actorId);
        if (actor == null)
        {
            throw new DataRetrievalFailureException("actor does not exist");
        }
        if (actorId != studentid && actor.getPermissionlevel() != UserPermission.CLUB_BOARD)
        {
            throw new DataRetrievalFailureException("user does not have permission to update this information");
        }

        userService.deleteByStudentid(studentid);
        return Optional.of(user);
    }

    @ApiOperation(value = "Enroll a user in a club")
    @PostMapping(path = "/users/add_club")
    public @ResponseBody User addClub (@RequestParam int studentid, @RequestParam int clubid)
     {
        User user = userService.findByStudentid(studentid);
        Club club = clubRepository.findById(clubid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        if (club == null)
        {
            throw new DataRetrievalFailureException("club does not exist");
        }

        Set<Club> newClubs = user.getClubs();
        newClubs.add(club);
        user.setClubs(newClubs);

        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Unenroll a user from a club")
    @PostMapping(path = "/users/remove_club")
    public @ResponseBody User removeClub(@RequestParam int studentid, @RequestParam int clubid)
     {
        User user = userService.findByStudentid(studentid);
        Club club = clubRepository.findById(clubid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        if (club == null)
        {
            throw new DataRetrievalFailureException("club does not exist");
        }
        
        user.clubs.remove(club);
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Find the clubs a given student has joined")
    @GetMapping(path = "/users/get_clubs")
    public @ResponseBody Iterable<Club> getClubs(@RequestParam int studentid)
    {
        return userService.findByStudentid(studentid).clubs;
    }

    @ApiOperation(value = "Add a user to a room")
    @PostMapping(path = "/users/add_room")
    public @ResponseBody User addRoom(@RequestParam int studentid, @RequestParam int roomid)
     {
        User user = userService.findByStudentid(studentid);
        Room room = roomRepository.findById(roomid).get();
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        if (room == null)
        {
            throw new DataRetrievalFailureException("room does not exist");
        }

        Set<Room> newRooms = user.getRooms();
        newRooms.add(room);
        user.setRooms(newRooms);

        userService.save(user);
        return user;
    }

    @ApiOperation(value = "Remove a user from a room")
    @PostMapping(path = "/users/remove_room")
    public @ResponseBody User removeRoom(@RequestParam int studentid, @RequestParam int roomid)
     {
        User user = userService.findByStudentid(studentid);
        Room room = roomRepository.findById(roomid).get();
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        if (room == null)
        {
            throw new DataRetrievalFailureException("room does not exist");
        }

        Set<Room> newRooms = user.getRooms();
        newRooms.remove(room);
        user.setRooms(newRooms);
        userService.save(user);

        return user;
    }

    @ApiOperation(value = "Update a user's permissions")
    @PostMapping(path = "/users/update_perms")
    public @ResponseBody User updatePerms(@RequestParam int studentid, @RequestParam int permInteger, @RequestParam int actorId) {
        User user = userService.findByStudentid(studentid);
        if (user == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }
        User actor = userService.findByStudentid(actorId);
        if (actor == null)
        {
            throw new DataRetrievalFailureException("actor does not exist");
        }

        if (permInteger >= UserPermission.values().length || permInteger < 0)
        {
            throw new DataRetrievalFailureException("permisison level does not exist");
        }
        
        if (actor.getPermissionlevel().ordinal() <= permInteger)
        {
            throw new DataRetrievalFailureException("user does not have permissions to change this user's permissions");
        }

        user.setPermissionlevel(UserPermission.values()[permInteger]);
        userService.save(user);
        return user;
    }

    @GetMapping(path = "/users/search_username")
    public @ResponseBody Iterable<User> findByUsername(@RequestParam String username)
    {
        return userService.findByUsername(username);
    }
}