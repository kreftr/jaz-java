package pl.edu.pjwstk.jaz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.models.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    final private EntityManager em;

    @Autowired
    public UserRepository(EntityManager em){
        this.em = em;
    }

    @Transactional
    public void add(User user){
        em.persist(user);
    }

    @Transactional
    public void remove(String username) {
        if (findByUsername(username).isPresent())
            em.remove(findByUsername(username).get());
    }

    @Transactional
    public Optional<User> findByUsername(String username){
        Optional user;
        try {
            user = Optional.of(em.createQuery("SELECT user FROM User user where user.username = :username")
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            user = Optional.empty();
        }
        return user;
    }

    @Transactional
    public Map<String, User> getUserMap() {
        List<User> userList = em.createQuery("SELECT user FROM User user", User.class).getResultList();
        //list to map
        Map<String, User> userReturnMap = new HashMap<>();
        for (User u: userList) {
            userReturnMap.put(u.getUsername(), u);
        }
        return userReturnMap;
    }
}
