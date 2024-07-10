package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.record.TripResponse;
import com.rocketseat.planner.record.TripResquest;
import com.rocketseat.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    ParticipantService participantService;

    public TripResponse create(TripResquest tripResquest){
        Trip newTrip = new Trip(tripResquest);
        this.tripRepository.save(newTrip);
        this.participantService.registerParticipantsToEvent(tripResquest.email_to_invite(), newTrip.getId());
        TripResponse response = new TripResponse(newTrip.getId(),
                                                newTrip.getDestination(),
                                                newTrip.getStartsAt(),
                                                newTrip.getEndsAt(),
                                                newTrip.getOwnerName(),
                                                newTrip.getOwnerEmail());
        return response;
    }

    public Optional<Trip> getTrip(UUID idTrip) {
        return tripRepository.findById(idTrip);
    }
}
