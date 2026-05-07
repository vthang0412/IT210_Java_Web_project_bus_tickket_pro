package com.example.busticket.config;

import com.example.busticket.model.Seat;
import com.example.busticket.model.Ticket;
import com.example.busticket.model.TicketStatus;
import com.example.busticket.repository.SeatRepository;
import com.example.busticket.repository.TicketRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
@EnableScheduling
public class AutoCancelTask {
  private final TicketRepository ticketRepo;
  private final SeatRepository seatRepo;

  public AutoCancelTask(TicketRepository ticketRepo, SeatRepository seatRepo) {
    this.ticketRepo = ticketRepo;
    this.seatRepo = seatRepo;
  }

  // runs every 5 minutes
  @Scheduled(fixedDelayString = "300000")
  @Transactional
  public void cancelOldPendingTickets() {
    Instant cutoff = Instant.now().minusSeconds(30 * 60); // 30 minutes
    List<Ticket> old = ticketRepo.findPendingOlderThan(Timestamp.from(cutoff));
    for (Ticket t : old) {
      try {
        t.setStatus(TicketStatus.CANCELLED);
        ticketRepo.save(t);
        Seat s = seatRepo.findByIdForUpdate(t.getSeat().getId()).orElse(null);
        if (s != null) {
          s.setStatus(com.example.busticket.model.SeatStatus.AVAILABLE);
          s.setHoldExpiresAt(null);
          seatRepo.save(s);
        }
      } catch (Exception ex) {
        // log and continue
      }
    }
  }
}
