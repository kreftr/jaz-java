package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.Section;
import pl.edu.pjwstk.jaz.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
    }

    public void addSection(Section section){
        sectionRepository.add(section);
    }

    public void removeSection(Section section){
        sectionRepository.remove(section);
    }

    public void editSection(Section section) {
        sectionRepository.edit(section);
    }

    public Optional<Section> findSectionById(Long id){
        return sectionRepository.findById(id);
    }

    public Optional<Section> findSectionByTitle(String title){
        return sectionRepository.findByTitle(title);
    }

    public Optional<List<Section>> findAll(){
        return sectionRepository.findAll();
    }
}
