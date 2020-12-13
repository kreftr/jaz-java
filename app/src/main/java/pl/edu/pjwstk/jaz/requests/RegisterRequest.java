package pl.edu.pjwstk.jaz.requests;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@Component
@RequestScope
public class RegisterRequest {

    private String username;
    private String password;

    public RegisterRequest(){}

    public RegisterRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
