package pl.edu.pjwstk.jaz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.requests.LoginRequest;
import pl.edu.pjwstk.jaz.services.AuthenticationService;


@RestController
@RequestMapping("/login")
public class LoginController {

    private AuthenticationService authenticationService;


    @Autowired
    public LoginController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){

        if(!authenticationService.isLogged()) {

            boolean correctPassword = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());

            if (correctPassword) return new ResponseEntity(HttpStatus.OK);
            else return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        } else return new ResponseEntity(HttpStatus.CONFLICT);
    }

    //!--- USER AND ADMIN PERMISSIONS ---!
    @PreAuthorize("hasAnyAuthority(USER, ADMIN)")
    @GetMapping("/user/logout")         //sets userSession fields to: logged = false and role = null
    public ResponseEntity logout(){
        authenticationService.logOut();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(USER, ADMIN)")
    @GetMapping("/user/logged")         //returns info that user is logged
    public String loggedInfo(){
        return "Logged in: "+authenticationService.isLogged();
    }

    //------------------------------------
}
