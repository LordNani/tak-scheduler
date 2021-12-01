package com.simpletak.takscheduler.api.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class AuthController {

    @GetMapping("/auth")
    public String loginPage(){
        return "auth";
    }
}
