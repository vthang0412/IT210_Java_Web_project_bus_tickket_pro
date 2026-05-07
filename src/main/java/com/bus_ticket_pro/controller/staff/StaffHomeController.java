package com.bus_ticket_pro.controller.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffHomeController {

    @GetMapping("/staff/home")
    public String home(){
        return "staff/home";
    }
}