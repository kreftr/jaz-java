package pl.edu.pjwstk.jaz.filters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import pl.edu.pjwstk.jaz.sessions.UserSession;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//AuthenticationFilter - a filter that checks whether the content requires to be logged in

@Order(1)   //sets the order in which filters are executed
public class AuthenticationFilter extends HttpFilter {

    private UserSession userSession;

    @Autowired
    public AuthenticationFilter(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (userSession.isLoggedIn()){                                  //AVAILABLE ONLY FOR LOGGED
            chain.doFilter(request, response);                         //if user is logged then pass all requests
        }
        else{                                                        //RESPONSE FOR EVERYONE UNAUTHORIZED
            response.setStatus(HttpStatus.UNAUTHORIZED.value());    //if user is not logged then return UNAUTHORIZED
        }
    }

}
