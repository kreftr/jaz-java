package pl.edu.pjwstk.jaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.edu.pjwstk.jaz.repository.UserRepository;
import pl.edu.pjwstk.jaz.services.UserService;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
