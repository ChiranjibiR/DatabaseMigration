package com.cloudops.DatabaseMigration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DatabaseMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseMigrationApplication.class, args);
	}

}
