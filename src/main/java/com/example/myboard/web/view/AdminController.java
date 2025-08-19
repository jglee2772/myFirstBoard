package com.example.myboard.web.view;

import com.example.myboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    // 관리자 페이지 (승인 대기 중인 사용자 목록)
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(Model model) {
        model.addAttribute("pendingUsers", userService.getPendingUsers());
        return "admin";
    }

    // 사용자 승인 처리
    @PostMapping("/admin/approve/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveUser(@PathVariable Long userId) {
        try {
            userService.approveUser(userId);
            return "redirect:/admin?message=approved";
        } catch (Exception e) {
            return "redirect:/admin?error=failed";
        }
    }
}
