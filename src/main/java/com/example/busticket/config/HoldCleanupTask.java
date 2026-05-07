package com.example.busticket.config;

import com.example.busticket.model.Seat;
import com.example.busticket.repository.SeatRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
@EnableScheduling
public class HoldCleanupTask {
  private final SeatRepository seatRepo;

  public HoldCleanupTask(SeatRepository seatRepo){ this.seatRepo = seatRepo; }

  @Scheduled(fixedDelayString = "60000")
  @Transactional
  public void releaseExpiredHolds(){
    List<Seat> seats = seatRepo.findAll();
    Instant now = Instant.now();
    for(Seat s : seats){
      if(s.getStatus() == com.example.busticket.model.SeatStatus.PENDING && s.getHoldExpiresAt() != null && s.getHoldExpiresAt().isBefore(now)){
        s.setStatus(com.example.busticket.model.SeatStatus.AVAILABLE);
        s.setHoldExpiresAt(null);
        seatRepo.save(s);
      }
    }
  }
}
