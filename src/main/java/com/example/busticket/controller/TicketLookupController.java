package com.example.busticket.controller;

import com.example.busticket.dto.TicketDetailDTO;
import com.example.busticket.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketLookupController {
  private final TicketRepository ticketRepo;

  public TicketLookupController(TicketRepository ticketRepo){ this.ticketRepo = ticketRepo; }

  @GetMapping("/ticket/lookup")
  public String form(){ return "ticket-lookup"; }

  @GetMapping("/ticket/find")
  public String find(@RequestParam String code, @RequestParam String phone, Model model){
    var opt = ticketRepo.findDetailByCodeAndPhone(code, phone);
    if(opt.isEmpty()) return "ticket-notfound";
    model.addAttribute("detail", opt.get());
    return "ticket-detail";
  }
}
