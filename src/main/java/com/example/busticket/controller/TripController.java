package com.example.busticket.controller;

import com.example.busticket.model.Seat;
import com.example.busticket.model.Ticket;
import com.example.busticket.repository.SeatRepository;
import com.example.busticket.repository.TripRepository;
import com.example.busticket.repository.TicketRepository;
import com.example.busticket.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trip")
public class TripController {
  private final TripRepository tripRepo;
  private final SeatRepository seatRepo;
  private final BookingService bookingService;
  private final TicketRepository ticketRepo;

  public TripController(TripRepository tripRepo, SeatRepository seatRepo, BookingService bookingService, TicketRepository ticketRepo){
    this.tripRepo = tripRepo; this.seatRepo = seatRepo; this.bookingService = bookingService; this.ticketRepo = ticketRepo;
  }

  @GetMapping("/{id}/seats")
  public String viewSeats(@PathVariable Long id, Model model){
    var tripOpt = tripRepo.findById(id);
    if(tripOpt.isEmpty()) return "redirect:/search";
    model.addAttribute("trip", tripOpt.get());
    List<Seat> seats = seatRepo.findByTripIdOrderBySeatNumber(id);
    model.addAttribute("seats", seats);
    return "trip-seats";
  }

  @PostMapping("/{id}/reserve")
  public String reserve(@PathVariable Long id, @RequestParam Long seatId, @RequestParam String customerName, @RequestParam String customerPhone, Model model){
    try{
      Ticket t = bookingService.reserveSeat(seatId, customerName, customerPhone);
      return "redirect:/ticket/" + t.getTicketCode();
    }catch(Exception ex){
      model.addAttribute("error", ex.getMessage());
      return viewSeats(id, model);
    }
  }
}
