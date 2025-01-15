package com.aurealab.api;

import com.aurealab.api.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

//	@Autowired
//	TemplateService templateService;
//
//	@Bean
//	CommandLineRunner init(){
//		return args -> {
//			System.out.println(templateService.createTemplate());
//		};
//	}

}
