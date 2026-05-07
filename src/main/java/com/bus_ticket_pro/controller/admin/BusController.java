package com.bus_ticket_pro.controller.admin;

import com.bus_ticket_pro.dto.bus.BusDTO;
import com.bus_ticket_pro.entity.Bus;
import com.bus_ticket_pro.service.admin.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/buses")
public class BusController {
    private final BusService busService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("buses", busService.findAll());
        return "admin/bus-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("bus", new BusDTO());
        return "admin/bus-form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("bus") BusDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/bus-form";
        }
        busService.create(dto);
        return "redirect:/admin/buses";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Bus bus = busService.findById(id);
        BusDTO dto = new BusDTO();
        BeanUtils.copyProperties(bus, dto);
        model.addAttribute("bus", dto);
        model.addAttribute("id", id);
        return "admin/bus-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute("bus") BusDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/bus-edit";
        }
        busService.update(id, dto);
        return "redirect:/admin/buses";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        try {

            busService.delete(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Xóa xe thành công"
            );

        } catch (Exception e){

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/admin/buses";
    }
}