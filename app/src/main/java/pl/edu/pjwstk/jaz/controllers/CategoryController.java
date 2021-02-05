package pl.edu.pjwstk.jaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.models.Category;
import pl.edu.pjwstk.jaz.models.Section;
import pl.edu.pjwstk.jaz.services.CategoryService;
import pl.edu.pjwstk.jaz.services.SectionService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private CategoryService categoryService;
    private SectionService sectionService;

    @Autowired
    public CategoryController(CategoryService categoryService, SectionService sectionService){
        this.categoryService = categoryService;
        this.sectionService = sectionService;
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PostMapping
    public ResponseEntity addCategory(@RequestParam(name = "section_id") Long section_id, @RequestParam(name = "title") String title){
        if(categoryService.findCategoryByTitle(title).isEmpty()){
            if (!sectionService.findSectionById(section_id).isEmpty()){
                if(!(title.isEmpty() && title.isBlank())){
                    Section section = sectionService.findSectionById(section_id).get();
                    section.addCategory(new Category(title, section_id));
                    sectionService.editSection(section);
                    return new ResponseEntity("Category created", HttpStatus.CREATED);
                }
                else return new ResponseEntity("Title cannot be empty or blank", HttpStatus.BAD_REQUEST);
            }
            else return new ResponseEntity("Section with this id does not exist", HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity("Category with this title already exists", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity removeCategory(@PathVariable(name = "id") Long id){
        if(!categoryService.findCategoryById(id).isEmpty()){

            Category category = categoryService.findCategoryById(id).get();
            categoryService.removeCategory(category);
            return new ResponseEntity("Category removed", HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PutMapping(path = "/{id}")
    public ResponseEntity editCategory(@PathVariable(name = "id") Long id, @RequestParam(name = "newTitle") String newTitle){
        if(!categoryService.findCategoryById(id).isEmpty()){
            if(!(newTitle.isEmpty() || newTitle.isBlank())){

                Category category = categoryService.findCategoryById(id).get();
                category.setTitle(newTitle);
                categoryService.editCategory(category);

                return new ResponseEntity("Category edited", HttpStatus.OK);
            }
            else return new ResponseEntity("Title cannot be empty or blank", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
