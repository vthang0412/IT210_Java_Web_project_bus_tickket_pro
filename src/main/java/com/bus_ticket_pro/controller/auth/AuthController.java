package com.bus_ticket_pro.controller.auth;

import com.bus_ticket_pro.dto.auth.RegisterRequest;
import com.bus_ticket_pro.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("registerRequest",
                new RegisterRequest());

        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid
            @ModelAttribute RegisterRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return "auth/register";
        }

        authService.register(request);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}