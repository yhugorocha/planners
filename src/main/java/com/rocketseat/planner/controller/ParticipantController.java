package com.rocketseat.planner.controller;

import com.rocketseat.planner.record.participant.ParticipantRequest;
import com.rocketseat.planner.record.participant.ParticipantResponse;
import com.rocketseat.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    ParticipantService participantService;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ParticipantResponse> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequest participant){
        return this.participantService.saveToConfirmationEmail(id, participant).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
