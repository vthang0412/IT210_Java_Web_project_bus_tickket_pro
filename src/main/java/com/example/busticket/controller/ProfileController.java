package com.example.busticket.controller;

import com.example.busticket.model.UserProfile;
import com.example.busticket.repository.UserRepository;
import com.example.busticket.repository.UserProfileRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
  private final UserRepository userRepo;
  private final UserProfileRepository profileRepo;

  public ProfileController(UserRepository userRepo, UserProfileRepository profileRepo){ this.userRepo = userRepo; this.profileRepo = profileRepo; }

  @GetMapping("/profile")
  public String viewProfile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, Model model){
    if(principal == null) return "redirect:/login";
    userRepo.findByUsername(principal.getUsername()).ifPresent(u -> {
      UserProfile p = profileRepo.findByUserId(u.getId()).orElse(new UserProfile());
      model.addAttribute("profile", p);
    });
    return "profile";
  }

  @PostMapping("/profile")
  public String saveProfile(@Valid @ModelAttribute("profile") UserProfile profile, BindingResult br){
    if(br.hasErrors()) return "profile";
    profileRepo.save(profile);
    return "redirect:/profile?updated";
  }
}
