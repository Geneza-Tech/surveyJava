package com.survey.geneza.service;
import com.survey.geneza.domain.Participant;
import java.util.List;

public interface ParticipantService {
    public Participant findById(Integer id);
    public void saveParticipant(Participant participant_1);
    public boolean deleteParticipant(Integer participantId);
    public List<Participant> findAll();
    public List<Participant> findAllByPersonId(Integer  person);
    public List<Participant> findAllByUserId(Integer userId);
}