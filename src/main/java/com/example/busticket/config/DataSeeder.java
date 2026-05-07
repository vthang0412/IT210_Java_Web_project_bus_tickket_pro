package com.example.busticket.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.busticket.model.*;
import com.example.busticket.repository.*;

@Component
public class DataSeeder implements CommandLineRunner {
  private final LocationRepository locationRepo;
  private final RouteRepository routeRepo;
  private final BusRepository busRepo;
  private final TripRepository tripRepo;

  public DataSeeder(LocationRepository l, RouteRepository r, BusRepository b, TripRepository t){ this.locationRepo = l; this.routeRepo = r; this.busRepo = b; this.tripRepo = t; }

  @Override
  public void run(String... args) throws Exception {
    if(locationRepo.count() == 0){
      Location hn = locationRepo.save(new Location()); hn.setName("Hà Nội");
      Location hp = locationRepo.save(new Location()); hp.setName("Hải Phòng");
      Location nd = locationRepo.save(new Location()); nd.setName("Nam Định");
      Location dn = locationRepo.save(new Location()); dn.setName("Đà Nẵng");
      Location sg = locationRepo.save(new Location()); sg.setName("TP Hồ Chí Minh");

      Route r1 = new Route(); r1.setFrom(hn); r1.setTo(hp); r1.setDistanceKm(120.0); routeRepo.save(r1);
      // minimal seed
    }
  }
}
