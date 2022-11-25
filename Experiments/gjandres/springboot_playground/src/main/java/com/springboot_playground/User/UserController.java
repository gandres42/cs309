package com.springboot_playground.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PutMapping(path="/users/create")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String password, @RequestParam String email) {
        User newUser = new User(name, password, email);
        userRepository.save(newUser);
        return "Saved";
    }

    @PostMapping(path="/users/login")
    public @ResponseBody String loginUser (@RequestParam String password, @RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (password.equals(user.getPassword()))
        {
            return user.getName();
        }
        return "login failed";
    }

    @GetMapping(path="/users/all")
    public @ResponseBody Iterable<User> getAllUsers() 
    {
        return userRepository.findAll();
    }
}