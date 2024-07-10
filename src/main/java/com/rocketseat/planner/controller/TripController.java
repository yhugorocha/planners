package com.rocketseat.planner.controller;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.record.TripResponse;
import com.rocketseat.planner.record.TripResquest;
import com.rocketseat.planner.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    TripService tripService;

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripResquest tripResquest){
        return new ResponseEntity<>(tripService.create(tripResquest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDatails(@PathVariable UUID id){
        return tripService.getTrip(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
