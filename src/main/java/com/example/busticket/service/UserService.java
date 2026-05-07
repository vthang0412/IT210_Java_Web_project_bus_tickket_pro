package com.example.busticket.service;

import com.example.busticket.model.User;
import com.example.busticket.repository.UserRepository;
import com.example.busticket.dto.RegisterDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepo;
  private final BCryptPasswordEncoder encoder;

  public UserService(UserRepository userRepo, BCryptPasswordEncoder encoder){ this.userRepo = userRepo; this.encoder = encoder; }

  @Transactional
  public User register(RegisterDto dto){
    if(userRepo.existsByUsername(dto.getEmail())) throw new IllegalArgumentException("Email đã tồn tại");
    User u = new User();
    u.setUsername(dto.getEmail());
    u.setPassword(encoder.encode(dto.getPassword()));
    u.setRole(com.example.busticket.model.Role.PASSENGER);
    return userRepo.save(u);
  }
}
