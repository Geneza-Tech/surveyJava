package com.survey.geneza.service.impl;

import com.survey.geneza.domain.Language;
import com.survey.geneza.domain.Participant;
import com.survey.geneza.domain.Person;
import com.survey.geneza.domain.Response;
import com.survey.geneza.domain.Survey;
import com.survey.geneza.persistence.LanguageRepository;
import com.survey.geneza.persistence.ParticipantRepository;
import com.survey.geneza.persistence.PersonRepository;
import com.survey.geneza.persistence.ResponseRepository;
import com.survey.geneza.persistence.SurveyRepository;
import com.survey.geneza.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PersonRepository personRepository;


        @Autowired
    private SurveyRepository surveyRepository; 

    @Override
@Transactional
public Language save(Language language) {
    // Ensure person is loaded
    Person person = personRepository.findById(language.getPersonId());
    if (person == null) {
        throw new RuntimeException("No person found with id " + language.getPersonId());
    }

    // Fetch participant(s) by personId
    List<Participant> participants = participantRepository.findAllByPersonId(person.getId());

    Participant participant;
    if (participants == null || participants.isEmpty()) {
        // No participant found → create one
        participant = new Participant();
        participant.setPerson(person);
        participant.setRole("DEFAULT_ROLE"); // 🔹 set a sensible default role or pass from request
        participant = participantRepository.save(participant);
    } else {
        // Use the first participant (or add logic if multiple)
        participant = participants.get(0);
    }

    // Set the person reference in language
    language.setPerson(person);

    // Save the language entity
    Language savedLanguage = languageRepository.save(language);

    // Fetch all surveys of type "Language"
    List<Survey> surveys = surveyRepository.findBySurveyType("Language");
    if (surveys == null || surveys.isEmpty()) {
        throw new RuntimeException("No surveys found of type 'Language'");
    }

    // Create a response for each survey
    for (Survey survey : surveys) {
        Response response = new Response();
        response.setLanguage(savedLanguage); // Link response to the saved language
        response.setParticipant(participant); // Assign the created or existing participant
        response.setSurvey(survey);           
        response.setLinkType("Language");     
        responseRepository.save(response);
    }

    return savedLanguage;
}


    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Language findById(Integer id) {
        return languageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        if (languageRepository.existsById(id)) {
            languageRepository.deleteById(id);
            return true;
        }
        return false;
    }

   @Override
public List<Language> findByPersonId(Integer personId) {
    Person person = personRepository.findById(personId);
if (person == null) {
    throw new RuntimeException("No person found with id " + personId);
}


    return languageRepository.findByPerson(person);
}





}
