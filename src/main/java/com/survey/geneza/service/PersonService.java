package com.survey.geneza.service;
import com.survey.geneza.domain.Person;
import java.util.List;

public interface PersonService {
    public Person findById(Integer id);
    public void savePerson(Person person_1);
    public boolean deletePerson(Integer personId);
    public List<Person> findAll();
}