package com.simpletak.takscheduler.api.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/view")
public class AuthController {

    @GetMapping("/auth")
    public String loginPage(HttpServletResponse response){
        Cookie cookie = new Cookie("token", "test");

        response.addCookie(cookie);
        return "auth";
    }
}
