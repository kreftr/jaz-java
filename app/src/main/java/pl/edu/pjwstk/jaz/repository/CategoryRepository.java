package pl.edu.pjwstk.jaz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.models.Category;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class CategoryRepository {

    final private EntityManager em;

    @Autowired
    public CategoryRepository(EntityManager em){
        this.em = em;
    }

    @Transactional
    public void add(Category category){
        em.persist(category);
    }

    @Transactional
    public void remove(Category category){
        em.remove(category);
    }

    @Transactional
    public void edit(Category category){
        em.merge(category);
    }

    @Transactional
    public Optional<Category> findById(Long id){
        Optional category;

        try {
            category = Optional.of(em.find(Category.class, id));
        } catch (Exception e){
            category = Optional.empty();
        }

        return category;
    }

    @Transactional
    public Optional<Category> findByTitle(String title){
        Optional category;

        try{
            category = Optional.of(em.createQuery("SELECT category FROM Category category where category.title = :title")
                    .setParameter("title", title).getSingleResult());
        } catch (Exception e){
            category = Optional.empty();
        }

        return category;
    }

}
