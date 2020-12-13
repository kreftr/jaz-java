package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.User;
import pl.edu.pjwstk.jaz.repository.UserRepository;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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
