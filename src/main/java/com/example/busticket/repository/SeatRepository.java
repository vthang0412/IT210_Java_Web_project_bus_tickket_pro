package com.example.busticket.repository;

import com.example.busticket.model.Seat;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from Seat s where s.id = :id")
  Optional<Seat> findByIdForUpdate(@Param("id") Long id);

  List<Seat> findByTripIdOrderBySeatNumber(Long tripId);
}
