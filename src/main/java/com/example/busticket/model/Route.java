package com.example.busticket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "routes")
public class Route {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_location_id")
  private Location from;

  @ManyToOne
  @JoinColumn(name = "to_location_id")
  private Location to;

  private Double distanceKm;
}
