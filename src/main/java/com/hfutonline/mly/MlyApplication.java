package com.hfutonline.mly;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlyApplication.class, args);
	}
}
