package edu.demo3.Club;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {

	@Autowired
	private ClubRepository repo;

    public Club findById(int id)
    {
        return repo.findById(id);
    }
    
    public void deleteById(int id)
    {
        repo.deleteById(id);
    }

    public Iterable<Club> findAll()
    {
        return repo.findAll();
    }

    public Club save(Club club)
    {
        return repo.save(club);
    }

    public List<Club> findByNameContaining(String name)
    {
        return repo.findByNameContaining(name);
    }
}
