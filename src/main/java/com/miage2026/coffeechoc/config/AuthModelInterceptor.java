package com.miage2026.coffeechoc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthModelInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            HttpSession session = request.getSession(false);
            boolean adminConnecte = session != null &&
                    Boolean.TRUE.equals(session.getAttribute(AdminInterceptor.SESSION_KEY));
            modelAndView.addObject("adminConnecte", adminConnecte);
        }
    }
}