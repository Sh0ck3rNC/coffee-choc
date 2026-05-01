package com.miage2026.coffeechoc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    public static final String SESSION_KEY = "adminAuthentifie";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean authentifie = session != null &&
                Boolean.TRUE.equals(session.getAttribute(SESSION_KEY));

        if (!authentifie) {
            String redirectUrl = request.getRequestURI();
            response.sendRedirect("/admin/login?redirect=" + redirectUrl);
            return false;
        }
        return true;
    }
}