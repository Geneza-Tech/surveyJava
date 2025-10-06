package com.survey.geneza.web.rest;

import com.survey.geneza.domain.Language;
import com.survey.geneza.persistence.LanguageRepository;
import com.survey.geneza.service.LanguageService;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Controller("LanguageRestController")
public class LanguageRestController {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageService languageService;

    // Create new Language
    @RequestMapping(value = "/Language", method = RequestMethod.POST)
    @ResponseBody
    public Language newLanguage(@RequestBody Language language) {
        languageService.save(language);  // inserts language + creates responses
        return languageRepository.findById(language.getId()).orElse(null);
    }

    // Update existing Language
    @RequestMapping(value = "/Language", method = RequestMethod.PUT)
    @ResponseBody
    public Language saveLanguage(@RequestBody Language language) {
        languageService.save(language);
        return languageRepository.findById(language.getId()).orElse(null);
    }

    // List all Languages
    @RequestMapping(value = "/Language", method = RequestMethod.GET)
    @ResponseBody
    public List<Language> listLanguages() {
        return new java.util.ArrayList<Language>(languageService.findAll());
    }

    // Load a specific Language by id
    @RequestMapping(value = "/Language/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Language loadLanguage(@PathVariable Integer id) {
        return languageService.findById(id);
    }

    // Delete a Language
    @RequestMapping(value = "/Language/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteLanguage(@PathVariable Integer id) {
        return languageService.delete(id);
    }

    // Find all Languages for a Person
    @RequestMapping(value = "/Language/Person/{personId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Language> getLanguagesByPersonId(@PathVariable Integer personId) {
        return new java.util.ArrayList<Language>(languageService.findByPersonId(personId));
    }

    // Paging support
    @RequestMapping(value = "/Language/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Language> findAllPaged(@PathVariable Integer page) {
        Sort sort = Sort.by(new Sort.Order(Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(page, 5, sort);
        return languageRepository.findAll(pageable);
    }

    // Paging + Sorting support
    @RequestMapping(value = "/Language/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Language> findAllPagedSorted(@PathVariable Integer page, 
                                             @PathVariable String sortField, 
                                             @PathVariable int direction) {
        Sort sort;
        if (direction == 1)
            sort = Sort.by(sortField).descending();
        else
            sort = Sort.by(sortField).ascending();

        Pageable sortedPaged = PageRequest.of(page, 10, sort);
        return languageRepository.findAll(sortedPaged);
    }

    
}
