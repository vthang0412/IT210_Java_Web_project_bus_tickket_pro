package com.bus_ticket_pro.repository;

import com.bus_ticket_pro.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
