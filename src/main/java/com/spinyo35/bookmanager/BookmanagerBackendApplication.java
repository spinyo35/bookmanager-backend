package com.spinyo35.bookmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.spinyo35.bookmanager")
public class BookmanagerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmanagerBackendApplication.class, args);
	}

}
