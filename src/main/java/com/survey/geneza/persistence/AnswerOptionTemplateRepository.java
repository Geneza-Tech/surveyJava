package com.survey.geneza.persistence;  
import com.survey.geneza.domain.AnswerOptionTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;


@Repository
public interface AnswerOptionTemplateRepository extends JpaRepository<AnswerOptionTemplate, Integer> {	 
    Optional<AnswerOptionTemplate> findById(Integer id);
    List<AnswerOptionTemplate> findAll();  
   Page<AnswerOptionTemplate> findAll(Pageable pageable);

}