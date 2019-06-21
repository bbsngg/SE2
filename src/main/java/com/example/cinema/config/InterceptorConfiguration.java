package com.example.cinema.config;

import com.example.cinema.interceptor.AdminInterceptor;
import com.example.cinema.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    public final static String SESSION_KEY = "user";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/login", "/index", "/signUp", "/register", "/error", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.jpeg", "/font/**").addPathPatterns("/**");
        //拦截正常操作但是要排除swagger所需的一些请求

        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**").excludePathPatterns("/swagger-ui.html","/login", "/index", "/signUp", "/register", "/error", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.jpeg", "/font/**").excludePathPatterns("/swagger-ui.html","/swagger-resources","/webjars/**","/v2/**","/configuration/**");
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
    }
}
