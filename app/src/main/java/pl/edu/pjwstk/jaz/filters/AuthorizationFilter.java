package pl.edu.pjwstk.jaz.filters;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import pl.edu.pjwstk.jaz.models.Role;
import pl.edu.pjwstk.jaz.sessions.UserSession;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//AuthorizationFilter - a filter that checks whether content is public or user needs to have proper rights to use it

@Order(2) //sets the order in which filters are executed
public class AuthorizationFilter extends HttpFilter {

    private UserSession userSession;

    public AuthorizationFilter(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (userSession.getRole() == Role.ADMIN){                //AVAILABLE ONLY FOR ADMIN ROLE
            chain.doFilter(request, response);                  //if user role is ADMIN then pass all requests
        }
        else{                                                       //RESPONSE FOR EVERYONE WITHOUT PERMISSION
            response.setStatus(HttpStatus.FORBIDDEN.value());      //if user does not have appropriate rights for the resource return FORBIDDEN
        }
    }

}
