package com.miage2026.coffeechoc.controller;

import com.miage2026.coffeechoc.config.AdminInterceptor;
import com.miage2026.coffeechoc.service.AdminAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.miage2026.coffeechoc.exception.UnauthorizedException;

@Controller
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @GetMapping("/admin/login")
    public String loginPage(@RequestParam(required = false) String redirect,
                            @RequestParam(required = false) String erreur,
                            Model model) {
        model.addAttribute("redirect", redirect != null ? redirect : "/admin/produits");
        model.addAttribute("erreur", erreur);
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String motDePasse,
                        @RequestParam(required = false) String redirect,
                        HttpSession session) {
        try {
            adminAuthService.verifierMotDePasse(motDePasse);
            session.setAttribute(AdminInterceptor.SESSION_KEY, true);
            return "redirect:" + (redirect != null ? redirect : "/admin/produits");
        } catch (UnauthorizedException e) {
            return "redirect:/admin/login?erreur=true&redirect=" +
                    (redirect != null ? redirect : "/admin/produits");
        }
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}