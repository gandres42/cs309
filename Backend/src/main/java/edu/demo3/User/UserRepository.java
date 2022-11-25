package edu.demo3.User;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByStudentid(int studentid);

    List<User> findAll();

    void deleteByStudentid(int studentid);
}