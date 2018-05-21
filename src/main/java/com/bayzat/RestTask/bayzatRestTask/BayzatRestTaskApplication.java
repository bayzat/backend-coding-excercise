package com.bayzat.RestTask.bayzatRestTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.*")
@EntityScan("com.*")
@EnableJpaRepositories("com.*")
public class BayzatRestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(BayzatRestTaskApplication.class, args);
	}
}
