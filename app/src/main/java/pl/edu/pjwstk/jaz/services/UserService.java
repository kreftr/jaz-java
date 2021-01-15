package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.Role;
import pl.edu.pjwstk.jaz.models.User;
import pl.edu.pjwstk.jaz.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void postConstruct() {
        if (!findUserByUsername("admin").isPresent()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin1"));
            user.setRole(Role.ADMIN);
            addUser(user);
        }
    }

    public void addUser(User user){
        userRepository.add(user);
    }

    public void removeUserByUsername(String username){
        userRepository.remove(username);
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Map<String, User> getUsersMap(){
        return userRepository.getUserMap();
    }
}
