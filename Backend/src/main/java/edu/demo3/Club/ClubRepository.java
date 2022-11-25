package edu.demo3.Club;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ClubRepository extends CrudRepository<Club, Integer> {
    Club findById(int id);
    
    void deleteById(int id);

    Iterable<Club> findAll();

    List<Club> findByNameContaining(String name);
}