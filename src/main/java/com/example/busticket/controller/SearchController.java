package com.example.busticket.controller;

import com.example.busticket.model.Location;
import com.example.busticket.model.Trip;
import com.example.busticket.repository.LocationRepository;
import com.example.busticket.repository.TripRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {
  private final LocationRepository locationRepo;
  private final TripRepository tripRepo;

  public SearchController(LocationRepository locationRepo, TripRepository tripRepo){ this.locationRepo = locationRepo; this.tripRepo = tripRepo; }

  @GetMapping("/search")
  public String searchForm(Model model){
    List<Location> locations = locationRepo.findAll();
    model.addAttribute("locations", locations);
    return "search";
  }

  @GetMapping("/search/results")
  public String results(@RequestParam Long from, @RequestParam Long to, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, Model model){
    List<Trip> trips = tripRepo.findAll().stream()
            .filter(t -> t.getRoute() != null && t.getRoute().getFrom() != null && t.getRoute().getTo() != null
                    && t.getRoute().getFrom().getId().equals(from)
                    && t.getRoute().getTo().getId().equals(to)
                    && t.getDepartureTime().toLocalDate().isEqual(date))
            .collect(Collectors.toList());
    model.addAttribute("trips", trips);
    return "search-results"; // template not created yet; listing raw trips
  }
}
