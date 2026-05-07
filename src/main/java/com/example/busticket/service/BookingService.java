package com.example.busticket.service;

import com.example.busticket.model.*;
import com.example.busticket.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class BookingService {
  private final SeatRepository seatRepo;
  private final TicketRepository ticketRepo;

  public BookingService(SeatRepository seatRepo, TicketRepository ticketRepo) {
    this.seatRepo = seatRepo; this.ticketRepo = ticketRepo;
  }

  @Transactional
  public Ticket reserveSeat(Long seatId, String customerName, String customerPhone) {
    Seat seat = seatRepo.findByIdForUpdate(seatId).orElseThrow(() -> new IllegalStateException("Seat không tồn tại"));

    if(seat.getStatus() == SeatStatus.PENDING && seat.getHoldExpiresAt() != null && seat.getHoldExpiresAt().isBefore(Instant.now())){
      seat.setStatus(SeatStatus.AVAILABLE);
      seat.setHoldExpiresAt(null);
    }

    if(seat.getStatus() != SeatStatus.AVAILABLE) throw new IllegalStateException("Ghế đã được đặt hoặc đang giữ");

    Instant holdUntil = Instant.now().plus(10, ChronoUnit.MINUTES);
    seat.setStatus(SeatStatus.PENDING);
    seat.setHoldExpiresAt(holdUntil);
    seatRepo.save(seat);

    Ticket t = new Ticket();
    t.setTicketCode(generateTicketCode());
    t.setCustomerName(customerName);
    t.setCustomerPhone(customerPhone);
    t.setSeat(seat);
    t.setTrip(seat.getTrip());
    t.setStatus(TicketStatus.PENDING);
    t.setTotalPrice(seat.getTrip().getPrice());
    ticketRepo.save(t);
    return t;
  }

  private String generateTicketCode(){ return UUID.randomUUID().toString().substring(0,8).toUpperCase(); }

  @Transactional
  public void confirmPayment(Long ticketId){
    Ticket t = ticketRepo.findById(ticketId).orElseThrow(() -> new IllegalStateException("Ticket không tồn tại"));
    Seat seat = seatRepo.findByIdForUpdate(t.getSeat().getId()).orElseThrow();
    t.setStatus(TicketStatus.PAID);
    ticketRepo.save(t);
    seat.setStatus(SeatStatus.BOOKED);
    seat.setHoldExpiresAt(null);
    seatRepo.save(seat);
  }

  @Transactional
  public void cancelTicket(Long ticketId){
    Ticket t = ticketRepo.findById(ticketId).orElseThrow();
    Seat seat = seatRepo.findByIdForUpdate(t.getSeat().getId()).orElseThrow();
    t.setStatus(TicketStatus.CANCELLED);
    ticketRepo.save(t);
    seat.setStatus(SeatStatus.AVAILABLE);
    seat.setHoldExpiresAt(null);
    seatRepo.save(seat);
  }
}
