package com.example.busticket.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Column(name = "created_at", insertable = false, updatable = false)
  private Timestamp createdAt;

  // getters and setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getUsername(){return username;}
  public void setUsername(String u){this.username=u;}
  public String getPassword(){return password;}
  public void setPassword(String p){this.password=p;}
  public Role getRole(){return role;}
  public void setRole(Role r){this.role=r;}
}
