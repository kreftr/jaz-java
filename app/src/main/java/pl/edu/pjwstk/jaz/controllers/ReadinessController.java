package pl.edu.pjwstk.jaz.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.models.Test1Entity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
@RestController
public class ReadinessController {
    private final EntityManager em;
    public ReadinessController(EntityManager em) {
        this.em = em;
    }
    @Transactional
    @GetMapping("/is-ready")
    public void isReady() {
        var entity = new Test1Entity();
        entity.setName("sdavsda");
        em.persist(entity);
    }
}
