package com.miage2026.coffeechoc.service;

import com.miage2026.coffeechoc.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    @Value("${admin.password}")
    private String adminPassword;

    public boolean verifierMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.isBlank()) {
            throw new UnauthorizedException("Le mot de passe ne peut pas être vide.");
        }
        if (!adminPassword.equals(motDePasse)) {
            throw new UnauthorizedException("Mot de passe incorrect.");
        }
        return true;
    }
}