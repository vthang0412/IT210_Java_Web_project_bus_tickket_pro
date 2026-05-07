package com.bus_ticket_pro.service.admin;

import com.bus_ticket_pro.dto.bus.BusDTO;
import com.bus_ticket_pro.entity.Bus;

import java.util.List;

public interface BusService {
    List<Bus> findAll();

    Bus findById(Long id);

    void create(BusDTO dto);

    void update(Long id, BusDTO dto);

    void delete(Long id);
}