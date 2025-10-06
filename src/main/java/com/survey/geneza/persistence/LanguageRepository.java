package com.survey.geneza.persistence;

import com.survey.geneza.domain.Language;
import com.survey.geneza.domain.Person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    List<Language> findByPerson(Person person);
    
}
