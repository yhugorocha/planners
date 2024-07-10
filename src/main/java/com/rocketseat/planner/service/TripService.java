package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.record.participant.ParticipantRequest;
import com.rocketseat.planner.record.trip.TripResponse;
import com.rocketseat.planner.record.trip.TripResquest;
import com.rocketseat.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        this.participantService.registerParticipantsToEvent(tripResquest.email_to_invite(), newTrip);
        TripResponse response = new TripResponse(newTrip.getId(),
                                                newTrip.getDestination(),
                                                newTrip.getStartsAt(),
                                                newTrip.getEndsAt(),
                                                newTrip.getOwnerName(),
                                                newTrip.getOwnerEmail());
        return response;
    }

    public Optional<TripResponse> getTrip(UUID idTrip) {
        return tripRepository.findById(idTrip).map(t -> new TripResponse(t.getId(), t.getDestination(), t.getStartsAt(), t.getEndsAt(), t.getOwnerName(), t.getOwnerEmail()));
    }

    public Optional<TripResponse> updateTrip(UUID id, TripResquest tripResquest) {
        Optional<TripResponse> tripResponse = this.tripRepository.findById(id).map(t -> {
            t.setDestination(tripResquest.destination());
            t.setStartsAt(LocalDateTime.parse(tripResquest.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            t.setEndsAt(LocalDateTime.parse(tripResquest.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            t.setOwnerName(tripResquest.owner_name());
            t.setOwnerEmail(tripResquest.owner_email());

            this.tripRepository.save(t);
            return new TripResponse(t.getId(),t.getDestination(), t.getStartsAt(), t.getEndsAt(), t.getOwnerName(), t.getOwnerEmail());
        });

        return tripResponse;
    }

    public Optional<TripResponse> confirmedTrip(UUID id) {
        Optional<TripResponse> tripResquest = this.tripRepository.findById(id).map(t -> {
            t.setIsConfirmed(true);
            this.tripRepository.save(t);
            return new TripResponse(t.getId(),t.getDestination(), t.getStartsAt(), t.getEndsAt(), t.getOwnerName(), t.getOwnerEmail());
        });

        return tripResquest;
    }

    public Optional<TripResponse> inviteToTrip(UUID id, ParticipantRequest participantRequest) {
        List<String> list = new ArrayList<>();
        list.add(participantRequest.email());
        Optional<TripResponse> tripResponse = this.tripRepository.findById(id).map(t -> {
            this.participantService.registerParticipantsToEvent(list, t);
            return new TripResponse(t.getId(),t.getDestination(), t.getStartsAt(), t.getEndsAt(), t.getOwnerName(), t.getOwnerEmail());
        });

        return tripResponse;
    }
}
