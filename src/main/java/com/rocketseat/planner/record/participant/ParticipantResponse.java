package com.rocketseat.planner.record.participant;

import com.rocketseat.planner.entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {

    private UUID id;
    private String name;
    private String email;
    private Boolean isConfirmed;
    private UUID tripId;

    public ParticipantResponse(UUID id, String email, UUID tripId){
        this.id = id;
        this.name = "";
        this.email = email;
        this.isConfirmed = false;
        this.tripId = tripId;
    }

    public ParticipantResponse (Participant participant){
        this.id = participant.getId();
        this.name = participant.getName();
        this.email = participant.getEmail();
        this.isConfirmed = participant.getIsConfirmed();
        this.tripId = participant.getTrip().getId();
    }
}
