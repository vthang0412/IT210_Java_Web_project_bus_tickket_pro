package com.example.busticket.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "route_id")
  private Route route;

  @ManyToOne
  @JoinColumn(name = "bus_id")
  private Bus bus;

  @Column(name = "departure_time")
  private LocalDateTime departureTime;

  private Double price;

  // getters/setters
}
