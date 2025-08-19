package com.example.myboard.service.security;

import com.example.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        // 승인되지 않은 사용자는 로그인 차단
        if (!u.isApproved() && !u.isAdmin()) {
            throw new UsernameNotFoundException("승인 대기 중인 사용자입니다. 관리자 승인을 기다려주세요.");
        }
        
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .roles(u.getRole()) // "USER" or "ADMIN"
                .build();
    } // Authentication 객체로 저장
}

// 로그인 시도 -> 스프링이 낚아챔 -> (이클래스에서) 일단 유저엔티티로 담아온다음 UserDetails 형태로 옮겨 담아서 가져감
// 거기서 또 꺼내서 결국은 Authentication
