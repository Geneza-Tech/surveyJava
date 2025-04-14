package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Response;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {	 
    Response findById(Integer id);
    List<Response> findAll();
    public List<Response> findAllBySurveyId(Integer surveyId);
    public List<Response> findAllByParticipantId(Integer participantId);
    
    @Query("SELECT r FROM Response r JOIN ResponseAnswers ra ON r.id = ra.response WHERE r.survey = :surveyId AND ra.question = :questionId")
    List<Response> findAllBySurveyIdAndQuestionId(Integer surveyId, Integer questionId); 

    @Query("SELECT r FROM Response r WHERE r.participant.person.id = :personId")
List<Response> findAllByPersonId(Integer personId);

   Page<Response> findAll(Pageable pageable);
   List<Response> findByLinkTypeAndLinkId(String linkType, Integer linkId);

}