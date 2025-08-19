package com.example.myboard;

import com.example.myboard.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyboardApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyboardApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserService userService) {
		return args -> {
			try {
				userService.createAdminAccount();
			} catch (Exception e) {
				// 관리자 계정이 이미 존재하는 경우 무시
			}
		};
	}
}
