package com.example.busticket.dto;

import com.example.busticket.model.TicketStatus;
import java.time.LocalDateTime;

public class TicketDetailDTO {
  private String ticketCode;
  private String customerName;
  private String customerPhone;
  private String licensePlate;
  private String busType;
  private String driverName;
  private String route;
  private LocalDateTime departureTime;
  private String seatNumber;
  private TicketStatus status;

  public TicketDetailDTO(String ticketCode, String customerName, String customerPhone, String licensePlate, String busType, String driverName, String route, LocalDateTime departureTime, String seatNumber, TicketStatus status) {
    this.ticketCode = ticketCode;
    this.customerName = customerName;
    this.customerPhone = customerPhone;
    this.licensePlate = licensePlate;
    this.busType = busType;
    this.driverName = driverName;
    this.route = route;
    this.departureTime = departureTime;
    this.seatNumber = seatNumber;
    this.status = status;
  }

  // getters
  public String getTicketCode() { return ticketCode; }
  public String getCustomerName() { return customerName; }
  public String getCustomerPhone() { return customerPhone; }
  public String getLicensePlate() { return licensePlate; }
  public String getBusType() { return busType; }
  public String getDriverName() { return driverName; }
  public String getRoute() { return route; }
  public LocalDateTime getDepartureTime() { return departureTime; }
  public String getSeatNumber() { return seatNumber; }
  public TicketStatus getStatus() { return status; }
}
