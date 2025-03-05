package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {	 
    Question findById(Integer id);
    List<Question> findAll();
    public List<Question> findAllBySurveyId(Integer surveyId);
    public List<Question> findAllByQuestionTypeId(Integer questionTypeId);
    public List<Question> findAllByParentQuestionId(Integer parentQuestionId);  
   Page<Question> findAll(Pageable pageable);

}