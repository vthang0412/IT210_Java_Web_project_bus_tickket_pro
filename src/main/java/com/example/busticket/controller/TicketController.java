package com.example.busticket.controller;

import com.example.busticket.dto.TicketDetailDTO;
import com.example.busticket.model.Ticket;
import com.example.busticket.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TicketController {
  private final TicketRepository ticketRepo;

  public TicketController(TicketRepository ticketRepo){ this.ticketRepo = ticketRepo; }

  @GetMapping("/ticket/lookup")
  public String lookupForm(){ return "ticket-lookup"; }

  @GetMapping("/ticket/{code}")
  public String viewTicket(@PathVariable String code, Model model){
    Optional<Ticket> t = ticketRepo.findByTicketCode(code);
    if(t.isEmpty()) return "ticket-notfound";
    model.addAttribute("ticket", t.get());
    return "ticket-detail";
  }
}
