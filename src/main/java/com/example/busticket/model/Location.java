package com.example.busticket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  // getters/setters
}
