package com.simpletak.takscheduler.api.view;

import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.user.UserInfoResponseDTO;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.service.eventgroup.EventGroupService;
import com.simpletak.takscheduler.api.service.tag.TagService;
import com.simpletak.takscheduler.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/view")
public class MainController {
    private final UserService userService;
    private final EventGroupService eventGroupService;
    private final TagService tagService;

    @Autowired
    public MainController(UserService userService,
                          EventGroupService eventGroupService,
                          TagService tagService) {
        this.userService = userService;
        this.eventGroupService = eventGroupService;
        this.tagService = tagService;
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            UserInfoResponseDTO user = userService.getUser((UUID) authentication.getDetails());
            String name = user.getFullName();
            model.addAttribute("name", name);
            addRoleAttribute(model, authentication);
            return "index";
        } else {
            return "auth";
        }
    }

    @GetMapping("/event-groups/search")
    public String eventGroups(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            addEventGroupsAttributes(model, List.of());
            addRoleAttribute(model, authentication);
            return "event-groups";
        } else {
            return "auth";
        }
    }

    @GetMapping("/search/by-tags")
    public String eventGroupsByTags(@RequestParam("tags") List<UUID> tags, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<EventGroupDTO> eventGroupDTOList = eventGroupService.getEventGroupsByTags(tags);
        addEventGroupsAttributes(model, eventGroupDTOList);
        addRoleAttribute(model, authentication);
        return "/event-groups";
    }
//
//    @GetMapping("/search/by-tags?")
//    public String eventGroupsByEmptyTagList(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        List<EventGroupDTO> eventGroupDTOList = eventGroupService.getEventGroupsByTags(new ArrayList<>());
//        addEventGroupsAttributes(model, eventGroupDTOList);
//        addRoleAttribute(model, authentication);
//        return "/event-groups";
//    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            UserInfoResponseDTO user = userService.getUser((UUID) authentication.getDetails());
            model.addAttribute("fullName", user.getFullName());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("id", user.getId());
            addRoleAttribute(model, authentication);
            return "profile";
        } else {
            return "auth";
        }
    }

    @GetMapping("/tag")
    public String tags(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            addRoleAttribute(model, authentication);
            if (!(boolean) model.getAttribute("isAdmin")) return "index";
            model.addAttribute("allTags", tagService.getAllTags());
            return "tag";
        } else {
            return "auth";
        }
    }

    @GetMapping("/create-event-group")
    public String createEventGroup(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            addRoleAttribute(model, authentication);
            return "create-event-group";
        } else {
            return "auth";
        }
    }

    @GetMapping("/my-event-groups")
    public String myEventGroups(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UUID) {
            addRoleAttribute(model, authentication);
            List<EventGroupDTO> eventGroupDTOS =
                    eventGroupService.getEventGroupsByUser(0, 50, (UUID) authentication.getDetails()).getContent();
            model.addAttribute("eventGroups", eventGroupDTOS);
            return "my-event-groups";
        } else {
            return "auth";
        }
    }


    private void addEventGroupsAttributes(Model model, List<EventGroupDTO> eventGroupDTOS) {
        model.addAttribute("allTags", tagService.getAllTags());
        model.addAttribute("eventGroups", eventGroupDTOS);
    }

    private void addRoleAttribute(Model model, Authentication authentication) {
        model.addAttribute("isAdmin", authentication.getAuthorities().toArray()[0].equals(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

}
