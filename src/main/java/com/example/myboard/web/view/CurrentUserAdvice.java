package com.example.myboard.web.view;

import com.example.myboard.domain.User;
import com.example.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class CurrentUserAdvice {
    private final UserRepository userRepository;

    @ModelAttribute("displayName")
    public String displayName(Authentication auth) {
        if (auth == null) return null;
        
        try {
            String email = auth.getName();
            return userRepository.findByEmail(email)
                    .map(User::getName)
                    .filter(name -> name != null && !name.isBlank())
                    .orElse(email.split("@")[0]); // 이메일에서 @ 앞부분 사용
        } catch (Exception e) {
            // 예외 발생 시 이메일에서 @ 앞부분만 사용
            String email = auth.getName();
            return email.split("@")[0];
        }
    }
    
    @ModelAttribute("isAdmin")
    public Boolean isAdmin(Authentication auth) {
        if (auth == null) return false;
        
        try {
            String email = auth.getName();
            return userRepository.findByEmail(email)
                    .map(User::isAdmin)
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }
}
