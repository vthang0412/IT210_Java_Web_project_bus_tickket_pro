package com.example.busticket.controller;

import com.example.busticket.dto.RegisterDto;
import com.example.busticket.model.Location;
import com.example.busticket.model.Trip;
import com.example.busticket.repository.LocationRepository;
import com.example.busticket.repository.TripRepository;
import com.example.busticket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {
  private final UserService userService;
  private final LocationRepository locationRepo;
  private final TripRepository tripRepo;

  public AuthController(UserService userService, LocationRepository locationRepo, TripRepository tripRepo){
    this.userService = userService; this.locationRepo = locationRepo; this.tripRepo = tripRepo;
  }

  @GetMapping("/register")
  public String registerForm(Model model){ model.addAttribute("registerDto", new RegisterDto()); return "register"; }

  @PostMapping("/register")
  public String registerSubmit(@Valid @ModelAttribute RegisterDto registerDto, BindingResult br, Model model){
    if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
      br.rejectValue("confirmPassword", "password.mismatch", "Mật khẩu xác nhận không khớp");
    }
    if(br.hasErrors()) return "register";
    try{
      userService.register(registerDto);
    }catch(Exception ex){
      br.rejectValue("email","error.register", ex.getMessage());
      return "register";
    }
    return "redirect:/login?registered";
  }

  @GetMapping("/login")
  public String login(){ return "login"; }
}
