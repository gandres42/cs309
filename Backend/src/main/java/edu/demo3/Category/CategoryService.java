package edu.demo3.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

    public Category findById(int id)
    {
        return repo.findById(id);
    }

    public List<Category> findAll()
    {
        return repo.findAll();
    }

    public void deleteById(int id)
    {
        repo.deleteById(id);
    }

    public Category save(Category category)
    {
        return repo.save(category);
    }
}
