package pl.edu.pjwstk.jaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.models.Section;
import pl.edu.pjwstk.jaz.services.SectionService;


import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {

    private SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PostMapping
    public ResponseEntity addSection(@RequestParam(name = "title") String title){

        if(sectionService.findSectionByTitle(title).isEmpty()){
            if(!(title.isEmpty() && title.isBlank())){
                sectionService.addSection(new Section(title));
                return new ResponseEntity("Section created", HttpStatus.CREATED);
            }
            else return new ResponseEntity("Title cannot be empty or blank", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity("Section with this title already exists", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity removeSection(@PathVariable Long id){

        if(sectionService.findSectionById(id).isPresent()){

            Section section = sectionService.findSectionById(id).get();
            sectionService.removeSection(section);
            return new ResponseEntity("Section removed", HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PutMapping(path = "/{id}")
    public ResponseEntity editSection(@PathVariable Long id, @RequestParam(name = "newTitle") String newTitle){

        if(!sectionService.findSectionById(id).isEmpty()){
            if(!(newTitle.isEmpty() || newTitle.isBlank())){

                Section section = sectionService.findSectionById(id).get();
                section.setTitle(newTitle);
                sectionService.editSection(section);

                return new ResponseEntity("Section edited", HttpStatus.OK);
            }
            else return new ResponseEntity("Title cannot be empty or blank", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @GetMapping
    public ResponseEntity getSectionList(){
        if(!sectionService.findAll().isEmpty()){
            List<Section> sectionList = sectionService.findAll().get();
            return new ResponseEntity(sectionList, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
