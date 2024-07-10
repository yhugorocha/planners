package com.rocketseat.planner.record;

import java.util.List;

public record TripResquest(String destination,
                           String starts_at,
                           String ends_at,
                           String owner_name,
                           String owner_email,
                           List<String> email_to_invite) {
}
