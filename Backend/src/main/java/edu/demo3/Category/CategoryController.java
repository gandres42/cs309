package edu.demo3.Category;

import io.swagger.annotations.ApiOperation;
import edu.demo3.Club.Club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DataRetrievalFailureException;
import java.util.Optional;


@RestController
public class CategoryController{
	@Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    @ApiOperation(value = "Create a new category")
    @PutMapping(path="/categories/create")
    public @ResponseBody Category createCategory(@RequestParam String name) {
        return categoryRepository.save(new Category(name));
    }


    @ApiOperation(value = "Get all categories")
    @GetMapping(path="/categories/all")
    public @ResponseBody Iterable<Category> getAllCategories() 
    {
        return categoryService.findAll();
    }

    @ApiOperation(value = "Find a category by category id")
    @GetMapping(path="/categories/search_category_id")
    public @ResponseBody Category getCategoryById(@RequestParam int id) 
    {
        Category category = categoryService.findById(id);
        if (category == null)
        {
            System.out.println(category);
            throw new DataRetrievalFailureException("category does not exist");
        }
        return category;
    }

    @ApiOperation(value = "Delete a category with id")
    @DeleteMapping(path="/categoriess/delete")
    public @ResponseBody Optional<Category> deleteUser(@RequestParam int id)
    {
        Category category = categoryService.findById(id);
        if (category == null)
        {
            throw new DataRetrievalFailureException("user does not exist");
        }

        categoryService.deleteById(id);
        return Optional.of(category);
    }


    @ApiOperation(value = "Find the clubs of a category with it's id")
    @GetMapping(path = "/categories/get_clubs")
    public @ResponseBody Iterable<Club> getClubs(@RequestParam int id)
    {
        return categoryService.findById(id).clubs;
    }

}