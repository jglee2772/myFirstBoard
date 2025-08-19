package com.example.myboard.service;

import com.example.myboard.domain.User;
import com.example.myboard.repository.UserRepository;
import com.example.myboard.web.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        userRepository.save(User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .role("USER")
                .build());
    }
    
    @Transactional
    public void createAdminAccount() {
        // 관리자 계정이 이미 존재하는지 확인
        if (userRepository.existsByEmail("admin@cyberboard.com")) {
            throw new IllegalArgumentException("관리자 계정이 이미 존재합니다.");
        }
        
        userRepository.save(User.builder()
                .email("admin@cyberboard.com")
                .password(passwordEncoder.encode("admin123"))
                .name("관리자")
                .role("ADMIN")
                .build());
    }
}
