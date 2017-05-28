package com.sheheryar.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication
public class CsvUploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvUploaderApplication.class, args);
	}
}
