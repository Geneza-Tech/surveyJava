package com.survey.geneza.service;

import com.survey.geneza.domain.Language;

import java.util.List;

public interface LanguageService {
    Language save(Language language);   // ✅ only takes Language
    List<Language> findAll();
    Language findById(Integer id);
    boolean delete(Integer id);
    List<Language> findByPersonId(Integer personId);
    
}
