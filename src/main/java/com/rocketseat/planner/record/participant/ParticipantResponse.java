package com.rocketseat.planner.record.participant;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.record.trip.TripResponse;

import java.util.UUID;

public record ParticipantResponse(UUID id, String name, String email, Boolean isConfirmed, Trip trip) {
}
