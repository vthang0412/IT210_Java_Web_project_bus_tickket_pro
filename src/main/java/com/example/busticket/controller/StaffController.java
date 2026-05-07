package com.example.busticket.controller;

import com.example.busticket.model.Ticket;
import com.example.busticket.repository.TicketRepository;
import com.example.busticket.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/staff")
public class StaffController {
  private final TicketRepository ticketRepo;
  private final BookingService bookingService;

  public StaffController(TicketRepository ticketRepo, BookingService bookingService){ this.ticketRepo = ticketRepo; this.bookingService = bookingService; }

  @GetMapping("/tickets/pending")
  public String pending(Model model){
    List<Ticket> list = ticketRepo.findAll().stream().filter(t -> t.getStatus().name().equals("PENDING")).collect(Collectors.toList());
    model.addAttribute("tickets", list);
    return "staff/pending";
  }

  @PostMapping("/tickets/{id}/confirm")
  public String confirm(@PathVariable Long id){ bookingService.confirmPayment(id); return "redirect:/staff/tickets/pending"; }

  @PostMapping("/tickets/{id}/cancel")
  public String cancel(@PathVariable Long id){ bookingService.cancelTicket(id); return "redirect:/staff/tickets/pending"; }
}
