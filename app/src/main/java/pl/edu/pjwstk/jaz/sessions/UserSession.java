package pl.edu.pjwstk.jaz.sessions;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.models.Role;


@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession{


    private boolean isLogged;
    private Role role;


    public UserSession(){
        this.isLogged = false;
    }


    public boolean isLoggedIn() {
        return isLogged;
    }

    public void logIn() {
        isLogged = true;
    }

    public void logOut() {
        setRole(null);
        isLogged = false;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
