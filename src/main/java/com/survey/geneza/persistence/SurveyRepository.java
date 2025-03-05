package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {	 
    Survey findById(Integer id);
    List<Survey> findAll();  
   Page<Survey> findAll(Pageable pageable);

}