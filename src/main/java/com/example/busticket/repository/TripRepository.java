package com.example.busticket.repository;

import com.example.busticket.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> { }
