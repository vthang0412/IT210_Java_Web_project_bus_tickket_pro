package com.bus_ticket_pro.controller.passenger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PassengerController {

    @GetMapping("/passenger/home")
    public String home(){

        return "passenger/home";
    }
}