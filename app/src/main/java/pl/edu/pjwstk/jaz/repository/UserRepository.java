package pl.edu.pjwstk.jaz.repository;

import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.models.Role;
import pl.edu.pjwstk.jaz.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private Map<String, User> userMap = new HashMap<>();

    UserRepository(){
        User admin = new User("admin","admin1");
        admin.setRole(Role.ADMIN);
        userMap.put("admin", admin);
    }

    public void add(User user){
        userMap.put(user.getUsername(), user);
    }

    public void remove(String username) { userMap.remove(username); }

    public Optional<User> findByUsername(String username){
        if (this.userMap.containsKey(username)) return Optional.of(userMap.get(username));
        else return Optional.empty();
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
