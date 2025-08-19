package com.example.myboard.web.view;

import com.example.myboard.service.UserService;
import com.example.myboard.web.dto.JoinRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller @RequiredArgsConstructor
public class AuthViewController {
    private final UserService userService;

    @GetMapping("/login") public String loginForm() {
        return "login";
    }

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(JoinRequest joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "join";
        }

        userService.join(joinRequest);
        return "redirect:/login";
    }
    
    // 관리자 계정 생성 (배포 후 한 번만 실행)
    @GetMapping("/create-admin")
    public String createAdmin() {
        try {
            userService.createAdminAccount();
            return "redirect:/login?message=Admin account created successfully";
        } catch (Exception e) {
            return "redirect:/login?error=Failed to create admin account";
        }
    }
}
