package com.simpletak.takscheduler.view.controller;

import com.simpletak.takscheduler.api.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class PersonController {
    private UserService userService;

    @GetMapping("/my-page")
    public String personPage(){
        return "person";
    }
}
