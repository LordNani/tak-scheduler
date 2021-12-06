package com.simpletak.takscheduler.api.view;

import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.user.UserInfoResponseDTO;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.service.eventgroup.EventGroupService;
import com.simpletak.takscheduler.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/view")
public class MainController {
    private final UserService userService;
    private final EventGroupService eventGroupService;

    @Autowired
    public MainController(UserService userService, EventGroupService eventGroupService) {
        this.userService = userService;
        this.eventGroupService = eventGroupService;
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            UserInfoResponseDTO user = userService.getUser((UUID) authentication.getDetails());
            String name = user.getFullName();
            model.addAttribute("name", name);
            return "index";
        } else {
            return "auth";
        }
    }

    @GetMapping("/event-groups")
    public String eventGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            return "event-groups";
        } else {
            return "auth";
        }
    }

    @GetMapping("/search/by-tags")
    public String eventGroupsByTags(@ModelAttribute List<UUID> tags, Model model){
        List<EventGroupDTO> eventGroupDTOList = eventGroupService.getEventGroupsByTags(tags);
        return "event-groups";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            UserInfoResponseDTO user = userService.getUser((UUID) authentication.getDetails());
            model.addAttribute("fullName", user.getFullName());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("id", user.getId());
            return "profile";
        } else {
            return "auth";
        }
    }

}
