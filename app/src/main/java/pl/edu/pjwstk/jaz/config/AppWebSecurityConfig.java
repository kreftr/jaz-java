package pl.edu.pjwstk.jaz.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pjwstk.jaz.filters.AuthenticationFilter;
import pl.edu.pjwstk.jaz.filters.AuthorizationFilter;
import pl.edu.pjwstk.jaz.sessions.UserSession;

//AppWebSecurityConfig - configuration file, specifying which filters are to run and under what paths

@Configuration
public class AppWebSecurityConfig {

    @Bean  //config for authentication
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(UserSession userSession){
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(userSession));
        registrationBean.addUrlPatterns("/login/user/*");
        registrationBean.addUrlPatterns("/register/admin/*");

        return registrationBean;
    }

    @Bean   //config for authorization
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(UserSession userSession){
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter(userSession));
        registrationBean.addUrlPatterns("/register/admin/*");

        return registrationBean;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
