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
				System.out.println("✅ 관리자 계정이 성공적으로 생성되었습니다!");
				System.out.println("📧 이메일: admin@cyberboard.com");
				System.out.println("�� 비밀번호: admin123");
			} catch (Exception e) {
				System.out.println("ℹ️ 관리자 계정이 이미 존재합니다: " + e.getMessage());
				// 관리자 계정이 이미 존재하는 경우 무시
			}
		};
	}
}
