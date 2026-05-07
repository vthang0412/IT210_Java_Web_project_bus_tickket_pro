package com.bus_ticket_pro.repository;

import com.bus_ticket_pro.entity.User;
import com.bus_ticket_pro.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository
        extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}