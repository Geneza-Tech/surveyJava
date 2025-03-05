package com.survey.geneza.persistence;  
import com.survey.geneza.domain.QuestionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {	 
    QuestionType findById(Integer id);
    List<QuestionType> findAll();  
   Page<QuestionType> findAll(Pageable pageable);

}