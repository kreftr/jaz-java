package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.Category;
import pl.edu.pjwstk.jaz.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category){
        categoryRepository.add(category);
    }

    public void removeCategory(Category category){
        categoryRepository.remove(category);
    }

    public void editCategory(Category category){
        categoryRepository.edit(category);
    }

    public Optional<Category> findCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<Category> findCategoryByTitle(String title){
        return categoryRepository.findByTitle(title);
    }
}
