package com.idp_core.idp_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.idp_core.idp_core.infrastructure.adapter.repository")
@EntityScan(basePackages = "com.idp_core.idp_core.infrastructure.adapter.entities")
public class IdpCoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(IdpCoreApplication.class, args);
	}
}
