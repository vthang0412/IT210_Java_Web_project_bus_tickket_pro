package com.bus_ticket_pro.controller.staff;

import com.bus_ticket_pro.dto.profile.ProfileDTO;
import com.bus_ticket_pro.entity.UserProfile;
import com.bus_ticket_pro.service.passenger.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff/profile")
public class StaffProfileController {

    private final ProfileService profileService;

    @GetMapping
    public String profile(
            Authentication authentication,
            Model model
    ){

        String username = authentication.getName();

        UserProfile profile =
                profileService.getProfile(username);

        ProfileDTO dto = new ProfileDTO();

        BeanUtils.copyProperties(profile,dto);

        model.addAttribute("profile",dto);

        return "staff/profile";
    }

    @PostMapping("/update")
    public String update(
            @Valid
            @ModelAttribute("profile")
            ProfileDTO dto,

            BindingResult result,

            Authentication authentication
    ){

        if(result.hasErrors()){
            return "staff/profile";
        }

        profileService.updateProfile(
                authentication.getName(),
                dto
        );

        return "redirect:/staff/home?success";
    }
}