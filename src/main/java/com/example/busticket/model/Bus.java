package com.example.busticket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buses")
public class Bus {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String licensePlate;
  private String busType;
  private Integer totalSeats;
  private String companyName;
  private String driverName;
}
