package com.anwenchu.caribbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class CaribbeanApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaribbeanApplication.class, args);
	}

}
