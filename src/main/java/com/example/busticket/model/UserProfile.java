package com.example.busticket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fullName;
  private String phone;
  private String email;
  private String address;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  // getters/setters omitted for brevity
}
