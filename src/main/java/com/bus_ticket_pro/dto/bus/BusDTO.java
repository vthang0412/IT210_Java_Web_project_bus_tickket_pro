package com.bus_ticket_pro.dto.bus;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BusDTO {

    @NotBlank(message = "Biển số không được để trống")
    private String licensePlate;

    @NotBlank(message = "Loại xe không được để trống")
    private String busType;

    @NotNull(message = "Số ghế không được để trống")
    @Min(value = 1,
            message = "Số ghế phải lớn hơn 0")
    private Integer totalSeats;

    @NotBlank(message = "Tên hãng xe không được để trống")
    private String companyName;

    @NotBlank(message = "Tên tài xế không được để trống")
    private String driverName;
}