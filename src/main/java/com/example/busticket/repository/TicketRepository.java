package com.example.busticket.repository;

import com.example.busticket.model.Ticket;
import com.example.busticket.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  Optional<Ticket> findByTicketCode(String code);

  @Query("SELECT new com.example.busticket.dto.TicketDetailDTO(t.ticketCode, t.customerName, t.customerPhone, b.licensePlate, b.busType, b.driverName, CONCAT(r.from.name,' → ', r.to.name), tr.departureTime, s.seatNumber, t.status) " +
         "FROM Ticket t " +
         "JOIN t.seat s " +
         "JOIN s.trip tr " +
         "JOIN tr.bus b " +
         "JOIN tr.route r " +
         "WHERE t.ticketCode = :code AND t.customerPhone = :phone")
  Optional<com.example.busticket.dto.TicketDetailDTO> findDetailByCodeAndPhone(@Param("code") String code, @Param("phone") String phone);

  @Query("SELECT t FROM Ticket t WHERE t.status = 'PENDING' AND t.bookingTime < :ts")
  List<Ticket> findPendingOlderThan(@Param("ts") Timestamp ts);
}
