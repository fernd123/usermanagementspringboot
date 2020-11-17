package es.masingenieros.infinisense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import es.masingenieros.infinisense.filestorage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class InfinisenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfinisenseApplication.class, args);
	}
}
