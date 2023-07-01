package com.edu.userms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class UsermsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermsApplication.class, args);
	}

}
