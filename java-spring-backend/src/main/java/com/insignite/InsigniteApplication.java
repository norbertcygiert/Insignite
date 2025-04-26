package com.insignite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.insignite")
public class InsigniteApplication {
	public static void main(String[] args) {
		SpringApplication.run(InsigniteApplication.class, args);
	}
}

