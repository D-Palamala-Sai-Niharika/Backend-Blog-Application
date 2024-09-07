package com.application.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BlogApplication {
	
	@Value("${blog.welcome.msg}")
	private String welcomeMsg;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@GetMapping("/")
	public String welcomeMsg() {
		//return "Welcome to my Blog Application";
		return welcomeMsg;
	}

}
