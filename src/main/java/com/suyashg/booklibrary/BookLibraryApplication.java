package com.suyashg.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.suyashg.booklibrary.model")
public class BookLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookLibraryApplication.class, args);
	}

}
