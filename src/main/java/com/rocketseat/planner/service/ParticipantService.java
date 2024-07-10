package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Participant;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.record.participant.ParticipantRequest;
import com.rocketseat.planner.record.participant.ParticipantResponse;
import com.rocketseat.planner.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        participantRepository.saveAll(participants);
        System.out.println(participants.get(0).getId());
    }

    public Optional<ParticipantResponse> saveToConfirmationEmail(UUID id, ParticipantRequest participant) {
        System.out.println("Chamou");
        Optional<ParticipantResponse> participantResponse = this.participantRepository.findById(id).map(p -> {
                                                    p.setName(participant.name());
                                                    p.setIsConfirmed(true);
                                                    this.participantRepository.save(p);
                                                    return new ParticipantResponse(p.getId(), p.getName(), p.getEmail(), p.getIsConfirmed(), p.getTrip());
        });

        return participantResponse;
    }

}
