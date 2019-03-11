package com.gullyipl.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableRdsInstance(dbInstanceIdentifier="${cloud.aws.rds.dbInstanceIdentifier}", username="${cloud.aws.rds.springaws.username}" , password="${cloud.aws.rds.springaws.password}")
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "/model")
@EnableJpaRepositories(basePackages = "com.gullyipl.userservice.repository")
@EnableEurekaClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
