package com.example.myboard.service;

import com.example.myboard.domain.User;
import com.example.myboard.repository.UserRepository;
import com.example.myboard.web.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        
        if (userRepository.existsByName(req.getName())) {
            throw new IllegalArgumentException("이미 등록된 사용자명입니다.");
        }

        userRepository.save(User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .role("USER")
                .approved(false) // 승인 대기 상태
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
                .approved(true) // 관리자는 자동 승인
                .build());
    }
    
    // 승인 대기 중인 사용자 목록 조회
    public List<User> getPendingUsers() {
        return userRepository.findByApprovedFalse();
    }
    
    // 사용자 승인
    @Transactional
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.approve();
        userRepository.save(user);
    }
    
    // 사용자 승인 상태 확인
    public boolean isUserApproved(String email) {
        return userRepository.findByEmail(email)
                .map(User::isApproved)
                .orElse(false);
    }
}
