package com.survey.geneza.persistence;  
import com.survey.geneza.domain.AnswerOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {	 
    AnswerOption findById(Integer id);
    List<AnswerOption> findAll();
    public List<AnswerOption> findAllByQuestionId(Integer questionId);  
   Page<AnswerOption> findAll(Pageable pageable);

}