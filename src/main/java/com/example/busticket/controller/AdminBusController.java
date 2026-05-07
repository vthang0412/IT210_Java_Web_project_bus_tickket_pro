package com.example.busticket.controller;

import com.example.busticket.model.Bus;
import com.example.busticket.repository.BusRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/buses")
public class AdminBusController {
  private final BusRepository busRepo;

  public AdminBusController(BusRepository busRepo){ this.busRepo = busRepo; }

  @GetMapping
  public String list(Model model){ model.addAttribute("buses", busRepo.findAll()); return "admin/buses/list"; }

  @GetMapping("/create")
  public String createForm(Model model){ model.addAttribute("bus", new Bus()); return "admin/buses/form"; }

  @PostMapping("/create")
  public String createSubmit(@Valid @ModelAttribute Bus bus, BindingResult br){
    if(br.hasErrors()) return "admin/buses/form";
    busRepo.save(bus);
    return "redirect:/admin/buses";
  }

  @GetMapping("/edit/{id}")
  public String editForm(@PathVariable Long id, Model model){
    busRepo.findById(id).ifPresent(b -> model.addAttribute("bus", b));
    return "admin/buses/form";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Long id){ busRepo.deleteById(id); return "redirect:/admin/buses"; }
}
