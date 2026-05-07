package com.example.busticket.controller;

import com.example.busticket.dto.ReservationSession;
import com.example.busticket.model.Ticket;
import com.example.busticket.repository.TicketRepository;
import com.example.busticket.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final TicketRepository ticketRepo;

    public BookingController(BookingService bookingService, TicketRepository ticketRepo) {
        this.bookingService = bookingService;
        this.ticketRepo = ticketRepo;
    }

    // Hold seat and create a PENDING ticket, store ticketCode in session
    @PostMapping("/trip/{id}/hold")
    public String holdSeat(@PathVariable Long id,
                           @RequestParam Long seatId,
                           @RequestParam String customerName,
                           @RequestParam String customerPhone,
                           HttpSession session,
                           Model model) {
        try {
            Ticket t = bookingService.reserveSeat(seatId, customerName, customerPhone);
            // store reservation in session
            session.setAttribute("reservation", new ReservationSession(t.getTicketCode()));
            return "redirect:/booking/confirm";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "error";
        }
    }

    // Show confirmation page
    @GetMapping("/booking/confirm")
    public String confirmBooking(HttpSession session, Model model) {
        Object o = session.getAttribute("reservation");
        if (o == null) {
            model.addAttribute("error", "Không có đặt chỗ đang chờ");
            return "error";
        }
        ReservationSession rs = (ReservationSession) o;
        var opt = ticketRepo.findByTicketCode(rs.getTicketCode());
        if (opt.isEmpty()) return "ticket-notfound";
        model.addAttribute("ticket", opt.get());
        return "booking-confirm";
    }

    // Complete booking flow (for passenger acknowledgement) - here just clear session and show ticket
    @PostMapping("/booking/complete")
    public String completeBooking(HttpSession session, Model model) {
        Object o = session.getAttribute("reservation");
        if (o == null) {
            model.addAttribute("error", "Không có đặt chỗ đang chờ");
            return "error";
        }
        ReservationSession rs = (ReservationSession) o;
        session.removeAttribute("reservation");
        return "redirect:/ticket/" + rs.getTicketCode();
    }

    @PostMapping("/booking/cancel-session")
    public String cancelSessionBooking(HttpSession session, Model model) {
        Object o = session.getAttribute("reservation");
        if (o != null) {
            ReservationSession rs = (ReservationSession) o;
            // attempt to cancel ticket to free seat
            try {
                var opt = ticketRepo.findByTicketCode(rs.getTicketCode());
                if (opt.isPresent()) bookingService.cancelTicket(opt.get().getId());
            } catch (Exception ex) {
                // ignore and continue clearing session
            }
            session.removeAttribute("reservation");
        }
        return "redirect:/search";
    }
}
