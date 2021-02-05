package pl.edu.pjwstk.jaz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class AppWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http
                .authorizeRequests()
                .antMatchers("/login", "/register", "/auction/all").permitAll()
                .antMatchers("/login/*", "/auction").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/register/admin/**", "/register/admin/*", "/section", "/section/*", "/parameter", "/parameter/*", "/category", "/category/*").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//    }
}