package com.example.busticket.repository;

import com.example.busticket.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
  @Query("select p from UserProfile p where p.user.id = :userId")
  Optional<UserProfile> findByUserId(@Param("userId") Long userId);
}
