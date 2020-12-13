package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.User;
import pl.edu.pjwstk.jaz.sessions.UserSession;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserService userService;
    private UserSession userSession;

    @Autowired
    public AuthenticationService(UserService userService, UserSession userSession){
        this.userService = userService;
        this.userSession = userSession;
    }

    public boolean login(String username, String password){

        Optional<User> loginAttempt = userService.findUserByUsername(username);

        if(loginAttempt.isPresent()){

            if(loginAttempt.get().getPassword().equals(password)) {

                userSession.logIn();
                userSession.setRole(loginAttempt.get().getRole());
                return true;
            }
            else return false;
        }
        else return false;
    }

    public boolean isLogged(){
        if (userSession.isLoggedIn()) return true;
        else return false;
    }

    public void logOut(){
        userSession.setRole(null);
        userSession.logOut();
    }

}
