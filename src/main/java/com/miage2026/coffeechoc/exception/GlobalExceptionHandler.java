package com.miage2026.coffeechoc.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Vérifie si la requête vient de l'API REST
    private boolean isApiRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleNotFound(ResourceNotFoundException ex,
                                 Model model, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", 404,
                    "error", "Not Found",
                    "message", ex.getMessage(),
                    "path", request.getRequestURI()
            ));
        }
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResource(Model model) {
        model.addAttribute("message", "La page demandée est introuvable.");
        return "error/404";
    }

    @ExceptionHandler(ConflictException.class)
    public Object handleConflict(ConflictException ex,
                                 Model model, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", 409,
                    "error", "Conflict",
                    "message", ex.getMessage(),
                    "path", request.getRequestURI()
            ));
        }
        model.addAttribute("message", ex.getMessage());
        return "error/409";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Object handleUnauthorized(UnauthorizedException ex,
                                     Model model, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", 401,
                    "error", "Unauthorized",
                    "message", ex.getMessage(),
                    "path", request.getRequestURI()
            ));
        }
        model.addAttribute("message", ex.getMessage());
        return "error/401";
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public Object handleTeapot(Model model, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", 418,
                    "error", "I'm a Teapot",
                    "message", "Je suis une théière, pas un café ☕",
                    "path", request.getRequestURI()
            ));
        }
        model.addAttribute("message", "Je suis une théière, pas un café ☕");
        return "error/418";
    }

    @ExceptionHandler(Exception.class)
    public Object handleGeneric(Exception ex,
                                Model model, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", 500,
                    "error", "Internal Server Error",
                    "message", ex.getMessage(),
                    "path", request.getRequestURI()
            ));
        }
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }
}