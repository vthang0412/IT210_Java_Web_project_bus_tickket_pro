package com.bus_ticket_pro.service.admin.impl;

import com.bus_ticket_pro.dto.bus.BusDTO;
import com.bus_ticket_pro.entity.Bus;
import com.bus_ticket_pro.repository.BusRepository;
import com.bus_ticket_pro.service.admin.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus findById(Long id) {
        return busRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy xe"));
    }

    @Override
    public void create(BusDTO dto) {
        if (busRepository.existsByLicensePlate(dto.getLicensePlate())) {
            throw new RuntimeException("Biển số đã tồn tại");
        }
        Bus bus = new Bus();
        BeanUtils.copyProperties(dto, bus);
        busRepository.save(bus);
    }

    @Override
    public void update(Long id, BusDTO dto) {
        Bus bus = findById(id);
        bus.setLicensePlate(dto.getLicensePlate());
        bus.setBusType(dto.getBusType());
        bus.setTotalSeats(dto.getTotalSeats());
        bus.setCompanyName(dto.getCompanyName());
        bus.setDriverName(dto.getDriverName());
        busRepository.save(bus);
    }

    @Override
    public void delete(Long id) {

        Bus bus = findById(id);

        try {

            busRepository.delete(bus);

        } catch (Exception e){

            throw new RuntimeException(
                    "Không thể xóa xe đang có chuyến đi"
            );
        }
    }
}