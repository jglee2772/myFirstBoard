package com.example.myboard.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "페이지를 찾을 수 없거나 오류가 발생했습니다.");
        return "error";
    }
}
