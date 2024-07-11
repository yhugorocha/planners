package com.rocketseat.planner.controller;

import com.rocketseat.planner.record.participant.ParticipantRequest;
import com.rocketseat.planner.record.participant.ParticipantResponse;
import com.rocketseat.planner.record.trip.TripResponse;
import com.rocketseat.planner.record.trip.TripResquest;
import com.rocketseat.planner.service.ParticipantService;
import com.rocketseat.planner.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    TripService tripService;

    @Autowired
    ParticipantService participantService;

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripResquest tripResquest){
        return new ResponseEntity<>(tripService.create(tripResquest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripDatails(@PathVariable UUID id){
        return tripService.getTrip(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponse> updateTrip(@PathVariable UUID id, @RequestBody TripResquest tripResquest){
        return tripService.updateTrip(id, tripResquest).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantResponse> inviteToTrip(@PathVariable UUID id, @RequestBody ParticipantRequest participantRequest){
        return tripService.inviteToTrip(id, participantRequest).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<TripResponse> confirmedTrip(@PathVariable UUID id){
        return tripService.confirmedTrip(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantResponse>> getParticipants(@PathVariable UUID id){
        return ResponseEntity.ok(participantService.getParticipantsToTripId(id).stream().map(p -> new ParticipantResponse(p)).toList());
    }
}
