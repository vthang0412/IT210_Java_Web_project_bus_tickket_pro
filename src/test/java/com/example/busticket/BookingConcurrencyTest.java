package com.example.busticket;

import com.example.busticket.model.*;
import com.example.busticket.repository.*;
import com.example.busticket.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class BookingConcurrencyTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TripRepository tripRepo;

    @Autowired
    private BusRepository busRepo;

    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    private TicketRepository ticketRepo;

    private Seat seat;

    @BeforeEach
    public void setup(){
        busRepo.deleteAll(); tripRepo.deleteAll(); seatRepo.deleteAll(); ticketRepo.deleteAll();
        Bus b = new Bus(); b.setLicensePlate("TEST-1"); b.setBusType("Test"); b.setTotalSeats(10); busRepo.save(b);
        Route r = new Route(); r.setFrom(new Location()); r.getFrom().setName("A"); r.setTo(new Location()); r.getTo().setName("B");
        // save route locations minimal via repo not injected here; instead create trip minimal
        Trip t = new Trip(); t.setBus(b); t.setDepartureTime(LocalDateTime.now().plusDays(1)); t.setPrice(100.0); tripRepo.save(t);
        seat = new Seat(); seat.setTrip(t); seat.setSeatNumber("1A"); seat.setStatus(SeatStatus.AVAILABLE); seatRepo.save(seat);
    }

    @Test
    public void concurrentReserve_shouldOnlyAllowOne() throws InterruptedException {
        int threads = 2;
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch start = new CountDownLatch(1);
        var executor = Executors.newFixedThreadPool(threads);
        var results = new java.util.concurrent.CopyOnWriteArrayList<String>();

        for(int i=0;i<threads;i++){
            executor.submit(() -> {
                ready.countDown();
                try{ start.await(); }
                catch(InterruptedException e){ }
                try{
                    var t = bookingService.reserveSeat(seat.getId(), "User", "091111111");
                    results.add("ok:"+t.getTicketCode());
                }catch(Exception ex){ results.add("err:"+ex.getMessage()); }
            });
        }
        ready.await(5, TimeUnit.SECONDS);
        start.countDown();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        long okCount = results.stream().filter(s->s.startsWith("ok:")).count();
        long errCount = results.stream().filter(s->s.startsWith("err:")).count();
        Assertions.assertEquals(1, okCount, "Exactly one reservation should succeed");
        Assertions.assertEquals(1, errCount, "One reservation should fail");
    }
}
