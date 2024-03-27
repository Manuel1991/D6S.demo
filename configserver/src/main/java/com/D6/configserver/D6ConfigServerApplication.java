package com.D6.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class D6ConfigServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(D6ConfigServerApplication.class, args);
	}
}
