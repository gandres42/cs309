package org.MC3.demo1;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    HashMap<String, String> users = new HashMap<String, String>();
    
    @RequestMapping(method = RequestMethod.POST, path = "/users/login")
    public String verifyUser(@RequestParam(value = "username", defaultValue = "void") String username, @RequestParam(value = "password", defaultValue = "void") String password) {
        if (users.containsKey(username))
        {
            if (users.get(username).equals(password))
            {
                return "User exists, welcome " + username;
            }
        }
        return "User login failed";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users/create")
    public String createUser(@RequestParam(value = "username", defaultValue = "void") String username, @RequestParam(value = "password", defaultValue = "void") String password)
    {
        if (!users.containsKey(username) && !username.equals("void"))
        {
            users.put(username, password);
            return "User created with username " + username;
        }
        return "Username is invalid or already exists";
    }
}