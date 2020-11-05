package es.masingenieros.infinisense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import es.masingenieros.infinisense.user.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class InfinisenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfinisenseApplication.class, args);
	}
}
