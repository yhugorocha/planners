package com.rocketseat.planner.record.trip;

import java.time.LocalDateTime;

import java.util.UUID;

public record TripResponse(UUID id,
                           String destination,
                           LocalDateTime starts_at,
                           LocalDateTime ends_at,
                           String owner_name,
                           String owner_email) {


}
