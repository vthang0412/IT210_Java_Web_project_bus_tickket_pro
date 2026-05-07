package com.bus_ticket_pro.repository;

import com.bus_ticket_pro.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
