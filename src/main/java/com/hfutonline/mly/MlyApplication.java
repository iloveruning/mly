package com.hfutonline.mly;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author chenliangliang
 */
@SpringBootApplication
@EnableAdminServer
@ServletComponentScan(basePackageClasses = com.hfutonline.mly.modules.api.filter.ApiFilter.class)
public class MlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlyApplication.class, args);
	}
}
