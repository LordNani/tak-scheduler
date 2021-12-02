package com.simpletak.takscheduler.api.config.security.jwt;

import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.user.CustomUserDetails;
import com.simpletak.takscheduler.api.service.authentication.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@Log
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl customUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String token = getTokenFromRequest(httpServletRequest);
        processJwtToken(token, jwtProvider, customUserDetailsService);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public static void processJwtToken(String token, JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            try {
                CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(userLogin);
                System.out.println("getAuthorities: " + customUserDetails.getAuthorities());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                customUserDetails,
                                null,
                                customUserDetails.getAuthorities()
                        );
                auth.setDetails(jwtProvider.getUserIdFromToken(token));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch (UserNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
