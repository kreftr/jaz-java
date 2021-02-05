package pl.edu.pjwstk.jaz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.models.Section;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class SectionRepository {

    final private EntityManager em;

    @Autowired
    public SectionRepository(EntityManager em){
        this.em = em;
    }

    @Transactional
    public void add(Section section){
        em.persist(section);
    }

    @Transactional
    public void remove(Section section){
        em.remove(section);
    }

    @Transactional
    public void edit(Section section){
        em.merge(section);
    }

    @Transactional
    public Optional<Section> findById(Long id){
        Optional section;

        try {
            section = Optional.of(em.find(Section.class, id));
        } catch (Exception e){
            section = Optional.empty();
        }

        return section;
    }

    @Transactional
    public Optional<Section> findByTitle(String title){
        Optional section;

        try {
            section = Optional.of(em.createQuery("SELECT section FROM Section section where section.title = :title")
                    .setParameter("title", title).getSingleResult());
        } catch (Exception e){
            section = Optional.empty();
        }

        return section;
    }

    @Transactional
    public Optional<List<Section>> findAll(){
        Optional sections;

        try {
            sections = Optional.of(em.createQuery("SELECT section FROM Section section", Section.class)
                    .getResultList());
        } catch (Exception e){
            sections = Optional.empty();
        }

        return sections;
    }
}
