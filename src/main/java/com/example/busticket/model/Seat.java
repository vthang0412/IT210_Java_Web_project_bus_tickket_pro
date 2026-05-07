package com.example.busticket.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "seats")
public class Seat {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "trip_id")
  private Trip trip;

  @Column(name = "seat_number")
  private String seatNumber;

  @Enumerated(EnumType.STRING)
  private SeatStatus status = SeatStatus.AVAILABLE;

  @Column(name = "hold_expires_at")
  private Instant holdExpiresAt;

  // getters/setters
}
