package com.example.busticket.controller;

import com.example.busticket.model.Ticket;
import com.example.busticket.repository.TicketRepository;
import com.example.busticket.service.BookingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@Controller
@RequestMapping("/ticket")
public class TicketController {
  private final TicketRepository ticketRepo;
  private final BookingService bookingService;

  public TicketController(TicketRepository ticketRepo, BookingService bookingService){ this.ticketRepo = ticketRepo; this.bookingService = bookingService; }

  @GetMapping("/{code}")
  public String viewTicket(@PathVariable String code, Model model){
    var t = ticketRepo.findByTicketCode(code);
    if(t.isEmpty()) return "ticket-notfound";
    model.addAttribute("ticket", t.get());
    return "ticket-detail";
  }

  @PostMapping("/{id}/cancel")
  public String cancelTicket(@PathVariable Long id, @AuthenticationPrincipal UserDetails user, Model model){
    var opt = ticketRepo.findById(id);
    if(opt.isEmpty()) { model.addAttribute("error", "Ticket không tồn tại"); return "error"; }
    Ticket t = opt.get();
    Instant now = Instant.now();
    Instant dep = t.getTrip().getDepartureTime().atZone(java.time.ZoneId.systemDefault()).toInstant();
    Duration diff = Duration.between(now, dep);
    if(diff.toHours() < 12) {
      model.addAttribute("error", "Không thể hủy vé trong vòng 12 giờ trước giờ khởi hành");
      return "error";
    }

    // allow only owner or staff/admin
    if(user != null && (user.getUsername().equals(t.getCustomerPhone()) || user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF") || a.getAuthority().equals("ROLE_ADMIN")))){
      bookingService.cancelTicket(id);
      return "redirect:/ticket/" + t.getTicketCode() + "?cancelled";
    }
    model.addAttribute("error", "Bạn không có quyền hủy vé này");
    return "error";
  }
}
