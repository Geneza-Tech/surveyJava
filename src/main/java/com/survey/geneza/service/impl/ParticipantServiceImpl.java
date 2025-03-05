package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ParticipantRepository;
import com.survey.geneza.domain.Participant;
import com.survey.geneza.service.ParticipantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ParticipantService")
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;
    public ParticipantServiceImpl() {
    }

    @Transactional
    public Participant findById(Integer id) {
        return participantRepository.findById(id);
    }

    @Transactional
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }
     
    @Transactional
    public void saveParticipant(Participant participant) {
        Participant existingParticipant = participantRepository.findById(participant.getId());
        if (existingParticipant != null) {
        if (existingParticipant != participant) {      
        existingParticipant.setId(participant.getId());
                existingParticipant.setPerson(participant.getPerson());
        }
        participant = participantRepository.save(existingParticipant);
    }else{
        participant = participantRepository.save(participant);
        }
        participantRepository.flush();
    }

    public boolean deleteParticipant(Integer participantId) {
        Participant participant = participantRepository.findById(participantId);
        if(participant!=null) {
            participantRepository.delete(participant);
            return true;
        }else {
            return false;
        }
    }@Transactional
    public List<Participant> findAllByPersonId(Integer  personId) {
        return new java.util.ArrayList<Participant>(participantRepository.findAllByPersonId(personId));
    }

    

}