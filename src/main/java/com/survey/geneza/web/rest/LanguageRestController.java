package com.survey.geneza.web.rest;

import com.survey.geneza.domain.Language;
import com.survey.geneza.persistence.LanguageRepository;
import com.survey.geneza.persistence.ParticipantRepository;
import com.survey.geneza.persistence.ResponseRepository;
import com.survey.geneza.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Language")
public class LanguageRestController {

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ParticipantRepository participantRepository;



    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Language newLanguage(@RequestBody Language language) {
        languageService.save(language);   // inserts language + creates 5 responses
        return languageRepository.findById(language.getId()).orElse(null);
    }

    @GetMapping
    public List<Language> listLanguages() {
        return languageService.findAll();
    }

    @GetMapping("/{id}")
    public Language getLanguage(@PathVariable Integer id) {
        return languageService.findById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteLanguage(@PathVariable Integer id) {
        return languageService.delete(id);
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<Language>> getLanguagesByPersonId(@PathVariable Integer personId) {
        List<Language> languages = languageService.findByPersonId(personId);
        return (languages != null && !languages.isEmpty()) 
                ? ResponseEntity.ok(languages) 
                : ResponseEntity.notFound().build();
    }
}
