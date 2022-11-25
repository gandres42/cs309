package edu.demo3.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

    public User findByStudentid(int studentid)
    {
        return repo.findByStudentid(studentid);
    }

    public List<User> findAll()
    {
        return repo.findAll();
    }

    public void deleteByStudentid(int studentid)
    {
        repo.deleteByStudentid(studentid);
    }

    public User save(User user)
    {
        return repo.save(user);
    }

    public Iterable<User> findByUsername(String username)
    {
        Iterable<User> users = repo.findAll();
        ArrayList<User> matching_users = new ArrayList<User>();
        for (User user : users)
        {
            if (user.getUsername().equals(username))
            {
                matching_users.add(user);
            }
        }
        return matching_users;
    }
}
