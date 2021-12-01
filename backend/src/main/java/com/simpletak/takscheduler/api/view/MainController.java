package com.simpletak.takscheduler.api.view;

import com.simpletak.takscheduler.api.dto.user.UserInfoResponseDTO;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/view")
@PreAuthorize("isAuthenticated()")
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserInfoResponseDTO user = userService.getUser((UUID) authentication.getDetails());
            String name = user.getFullName();
            model.addAttribute("name", name);
            return "index";
        }
        catch (Exception e){
            return "auth";
        }
    }

    @GetMapping("/event-groups")
    public String eventGroups(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return "auth";
        return "event-groups";
    }
}
