package com.gullyipl.matchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.gullyipl.matchservice.repository")
public class MatchServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MatchServiceApplication.class, args);
	}

}
