package pl.edu.pjwstk.jaz.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.edu.pjwstk.jaz.models.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAuthentication extends AbstractAuthenticationToken {

    private final User authenticatedUser;


    public UserAuthentication(User authenticatedUser) {
        super(toGrantedAuthorities(authenticatedUser.getAuthorities()));
        this.authenticatedUser = authenticatedUser;
        setAuthenticated(true);
    }

    private static Collection<? extends GrantedAuthority> toGrantedAuthorities(Set<String> authority) {
        return authority.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }



    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }
}
