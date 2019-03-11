package com.gullyipl.matchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableRdsInstance(dbInstanceIdentifier="${cloud.aws.rds.dbInstanceIdentifier}", username="${cloud.aws.rds.springaws.username}" , password="${cloud.aws.rds.springaws.password}")
@EnableJpaRepositories(basePackages = "com.gullyipl.matchservice.repository")
public class MatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchServiceApplication.class, args);
	}

}
