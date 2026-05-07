package com.example.busticket.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
public class Ticket {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ticket_code", unique = true)
  private String ticketCode;

  private String customerName;
  private String customerPhone;
  private String customerEmail;

  @ManyToOne
  @JoinColumn(name = "trip_id")
  private Trip trip;

  @ManyToOne
  @JoinColumn(name = "seat_id")
  private Seat seat;

  private Double totalPrice;

  @Enumerated(EnumType.STRING)
  private TicketStatus status = TicketStatus.PENDING;

  @Column(name = "booking_time", insertable = false, updatable = false)
  private Timestamp bookingTime;

  // getters/setters
}
