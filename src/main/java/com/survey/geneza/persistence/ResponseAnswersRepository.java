package com.survey.geneza.persistence;  
import com.survey.geneza.domain.ResponseAnswers;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface ResponseAnswersRepository extends JpaRepository<ResponseAnswers, Long> {	 
    ResponseAnswers findById(Integer id);
    List<ResponseAnswers> findAll();
    public List<ResponseAnswers> findAllByQuestionId(Integer questionId);
    public List<ResponseAnswers> findAllByAnswerOptionId(Integer answerOptionId);
    public List<ResponseAnswers> findAllByResponseId(Integer responseId);  
   Page<ResponseAnswers> findAll(Pageable pageable);
    @Modifying
    @Query("DELETE FROM ResponseAnswers ra WHERE ra.response.id = :responseId AND ra.question.id IN :questionIds")
    void deleteByResponseIdAndQuestionIds(@Param("responseId") Integer responseId,
                                          @Param("questionIds") List<Integer> questionIds);



}