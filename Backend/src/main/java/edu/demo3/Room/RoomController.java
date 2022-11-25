package edu.demo3.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.demo3.User.User;
import edu.demo3.User.UserRepository;
import io.swagger.annotations.ApiOperation;

@RestController
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get all created rooms")
    @GetMapping(path="/rooms/all")
    public @ResponseBody Iterable<Room> getAllClubs() 
    {
        return roomRepository.findAll();
    }

    @ApiOperation(value = "Create a new room")
    @PutMapping(path="/rooms/create")
    public @ResponseBody Room addNewclub (@RequestParam String name) {        
        Room newroom = new Room(name);
        roomRepository.save(newroom);
        return newroom;
    }

    @ApiOperation(value = "Finds all dm rooms a student is in")
    @GetMapping(path="/rooms/filter_by_studentid")
    public @ResponseBody Iterable<Room> getByStudentid(@RequestParam int studentid) 
    {
        List<Room> userRooms = new ArrayList<Room>();
        Iterable<Room> rooms = roomRepository.findAll();
        User user = userRepository.findByStudentid(studentid);
        for (Room room : rooms)
        {
            if (room.getUsers().contains(user) && !userRooms.contains(room))
            {
                userRooms.add(room);
            }
        }
        return userRooms;
    }

    @ApiOperation(value = "Delete a room")
    @DeleteMapping(path="/rooms/delete")
    public @ResponseBody void addNewclub (@RequestParam int roomid) {       
        Room todelete = roomRepository.findById(roomid).get();
        for (User user : todelete.getUsers())
        {
            Set<Room> newRooms = user.getRooms();
            newRooms.remove(todelete);
            user.setRooms(newRooms);
            userRepository.save(user);
        }
        roomRepository.deleteById(roomid);
    }

    @PostMapping(path = "/rooms/change_title")
    public @ResponseBody Room setTitle(@RequestParam int roomid, @RequestParam String title)
    {
        Room room = roomRepository.findById(roomid).get();
        room.setName(title);
        return roomRepository.save(room);
    }
}
