package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.PersonRepository;
import com.survey.geneza.domain.Person;
import com.survey.geneza.service.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PersonService")
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    public PersonServiceImpl() {
    }

    @Transactional
    public Person findById(Integer id) {
        return personRepository.findById(id);
    }

    @Transactional
    public List<Person> findAll() {
        return personRepository.findAll();
    }
     
    @Transactional
    public void savePerson(Person person) {
        Person existingPerson = personRepository.findById(person.getId());
        if (existingPerson != null) {
        if (existingPerson != person) {      
        existingPerson.setId(person.getId());
                existingPerson.setEmail(person.getEmail());
                existingPerson.setFirstName(person.getFirstName());
                existingPerson.setLastName(person.getLastName());
                existingPerson.setPhone(person.getPhone());
        }
        person = personRepository.save(existingPerson);
    }else{
        person = personRepository.save(person);
        }
        personRepository.flush();
    }

    public boolean deletePerson(Integer personId) {
        Person person = personRepository.findById(personId);
        if(person!=null) {
            personRepository.delete(person);
            return true;
        }else {
            return false;
        }
    }

    

}