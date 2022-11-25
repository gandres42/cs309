package edu.demo3.Category;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category findById(int id);

    List<Category> findAll();

    void deleteById(int id);
}