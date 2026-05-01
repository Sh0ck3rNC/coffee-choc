package com.miage2026.coffeechoc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminInterceptor adminInterceptor;
    private final AuthModelInterceptor authModelInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**")
                .excludePathPatterns("/admin/login", "/admin/logout");

        registry.addInterceptor(authModelInterceptor)
                .addPathPatterns("/**");
    }
}