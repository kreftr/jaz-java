package pl.edu.pjwstk.jaz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.models.Auction;
import pl.edu.pjwstk.jaz.models.Parameter;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class ParameterRepository {

    final private EntityManager em;

    @Autowired
    public ParameterRepository(EntityManager em){
        this.em = em;
    }


    @Transactional
    public void add(Parameter parameter){
        em.persist(parameter);
    }

    @Transactional
    public void remove(Parameter parameter){
        em.remove(parameter);
    }

    @Transactional
    public void edit(Parameter parameter){
        em.merge(parameter);
    }

    @Transactional
    public Optional<Parameter> findById(Long id){
        Optional parameter;

        try {
            parameter = Optional.of(em.find(Parameter.class, id));
        } catch (Exception e){
            parameter = Optional.empty();
        }

        return parameter;
    }

    @Transactional
    public Optional<Parameter> findByKey(String key){
        Optional parameter;

        try{
            parameter = Optional.of(em.createQuery("SELECT parameter FROM Parameter parameter where parameter.key = :key")
                    .setParameter("key", key).getSingleResult());
        } catch (Exception e){
            parameter = Optional.empty();
        }

        return parameter;
    }

    @Transactional
    public Optional<List<Parameter>> findAll(){
        Optional params;
        try{
            params = Optional.of(em.createQuery("SELECT p FROM Parameter p", Parameter.class).getResultList());
        }
        catch (Exception e){
            params = Optional.empty();
        }
        return params;
    }
}
