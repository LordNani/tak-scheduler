package com.simpletak.takscheduler.api.config.security;

import com.simpletak.takscheduler.api.config.security.jwt.JwtFilter;
import com.simpletak.takscheduler.api.config.security.jwt.JwtProvider;
import com.simpletak.takscheduler.api.service.authentication.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
public class CookieFilter extends HttpFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl customUserDetailsService;

    @Autowired
    public CookieFilter(JwtProvider jwtProvider, UserDetailsServiceImpl customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Optional<String> cookie = Arrays.stream(cookies)
                    .filter(c -> Objects.equals(c.getName(), "token"))
                    .map(Cookie::getValue)
                    .findFirst();
            if (cookie.isPresent()) {
                JwtFilter.processJwtToken(cookie.get(), jwtProvider, customUserDetailsService);
            }
        }
        chain.doFilter(request, response);
    }
}
