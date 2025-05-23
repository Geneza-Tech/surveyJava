package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {	 
    Person findById(Integer id);
    List<Person> findAll();  
   Page<Person> findAll(Pageable pageable);

}