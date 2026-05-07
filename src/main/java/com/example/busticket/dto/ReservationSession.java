package com.example.busticket.dto;

import java.io.Serializable;

public class ReservationSession implements Serializable {
    private String ticketCode;

    public ReservationSession() {}

    public ReservationSession(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }
}
