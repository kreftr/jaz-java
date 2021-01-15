package pl.edu.pjwstk.jaz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.models.User;
import pl.edu.pjwstk.jaz.requests.RegisterRequest;
import pl.edu.pjwstk.jaz.services.UserService;

import java.util.Map;


@RestController
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){

        if (!(registerRequest.getUsername() == null || registerRequest.getPassword() == null)&&
                !(registerRequest.getUsername().isBlank() || registerRequest.getPassword().isBlank())){

            if (!userService.findUserByUsername(registerRequest.getUsername()).isPresent()) {

                userService.addUser(new User(registerRequest.getUsername(),
                        passwordEncoder.encode(registerRequest.getPassword())));
                return new ResponseEntity(HttpStatus.CREATED);

            } else return new ResponseEntity(HttpStatus.CONFLICT);

        } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    //!------ ADMIN ONLY PERMISSIONS ------!

    @GetMapping("/admin/remove")            //remove user
    public ResponseEntity remove(@RequestParam(value = "user") String username){
        if(userService.findUserByUsername(username).isPresent()){
            userService.removeUserByUsername(username);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/users")             //return users map
    public Map<String, User> getUsersList(){
        return userService.getUsersMap();
    }

    //--------------------------------------
}
